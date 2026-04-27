package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.semestr.service.AdminOAuthService;

import java.io.IOException;

@Hidden
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class GitHubOAuthController {

    private final AdminOAuthService adminOAuthService;

    @GetMapping("/github/callback")
    public String githubCallback(@RequestParam("code") String code) {
        return adminOAuthService.handleGithubOAuthCallback(code);
    }

    @GetMapping("/github")
    public void redirectToGithub(HttpServletResponse response) throws IOException {
        adminOAuthService.redirectToGithub(response);
    }
}
