package ru.itis.semestr.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.itis.semestr.config.AppConfig;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.dto.response.GitHubAccessTokenResponse;
import ru.itis.semestr.dto.response.GitHubUserResponse;
import ru.itis.semestr.entity.GitHubAccountEntity;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.repository.GitHubAccountRepository;
import ru.itis.semestr.service.AdminOAuthService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class AdminOAuthServiceImpl implements AdminOAuthService {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private final RestTemplate restTemplate;
    private final GitHubAccountRepository gitHubAccountRepository;

    public AdminOAuthServiceImpl(
            RestTemplate restTemplate,
            GitHubAccountRepository gitHubAccountRepository,
            AppConfig appConfig
    ) {
        this.clientId = appConfig.githubConfig().getClientId();
        this.clientSecret = appConfig.githubConfig().getClientSecret();
        this.redirectUri = appConfig.githubConfig().getClientUri();
        this.restTemplate = restTemplate;
        this.gitHubAccountRepository = gitHubAccountRepository;
    }

    @Override
    public void redirectToGithub(HttpServletResponse response) throws IOException {
        String redirectUrl = String.format(
                "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=read:user",
                clientId,
                redirectUri
        );
        response.sendRedirect(redirectUrl);
    }


    @Override
    public String handleGithubOAuthCallback(String code) {
        String accessToken = exchangeCodeForAccessToken(code);
        if (accessToken == null) {
            String errorMessage = URLEncoder.encode("Неуспешная попытка GitHub OAuth", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        }

        String githubUsername = fetchGithubUsername(accessToken);
        if (githubUsername == null) {
            String errorMessage = URLEncoder.encode("Ошибка GitHub API", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        }

        System.out.println("githubUsername: " + githubUsername);

        Optional<GitHubAccountEntity> accountOpt = gitHubAccountRepository.findByGithubUsername(githubUsername);
        if (accountOpt.isEmpty()) {
            String errorMessage = URLEncoder.encode("У вашего GitHub нет доступа к панели администратора!", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        }

        UserEntity user = accountOpt.get().getUser();
        if (!"admin".equals(user.getRole())) {
            String errorMessage = URLEncoder.encode("Вы не администратор!", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + errorMessage;
        }

        loginUser(user);
        return "redirect:/admin/main";
    }

    private String exchangeCodeForAccessToken(String code) {
        String tokenUrl = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<GitHubAccessTokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                GitHubAccessTokenResponse.class
        );
        return response.getBody() != null ? response.getBody().getAccessToken() : null;
    }

    private String fetchGithubUsername(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitHubUserResponse> response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                entity,
                GitHubUserResponse.class
        );
        return response.getBody() != null ? response.getBody().getLogin() : null;
    }

    private void loginUser(UserEntity user) {
        UserPrincipal userPrincipal = new UserPrincipal(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userPrincipal,
                null,
                userPrincipal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
