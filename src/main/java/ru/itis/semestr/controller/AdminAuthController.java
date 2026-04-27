package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.semestr.config.AppConfig;
import ru.itis.semestr.config.security.UserPrincipal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Hidden
@Controller
@RequestMapping("/admin")
public class AdminAuthController {
    private final String SECRET_KEY;

    public AdminAuthController(
            AppConfig appConfig
    ) {
        this.SECRET_KEY = appConfig.securityKey().getSecretKey();
    }

    @GetMapping("/signIn")
    public String getAdminSignInPage() {
        return "adminSignIn";
    }

    @PostMapping("/signIn")
    public String signInAdmin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("codeword") String codeword,
            HttpServletRequest request) throws ServletException {

        if (!codeword.equals(SECRET_KEY)) {
            String msg = URLEncoder.encode("Неверное кодовое слово", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + msg;
        }

        try {
            request.login(email, password);
        } catch (ServletException e) {
            String msg = URLEncoder.encode("Вход не удался, попробуйте еще раз.", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + msg;
        }

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!principal.getUser().getRole().equals("admin")) {
            request.logout();
            String msg = URLEncoder.encode("Вы не администратор!", StandardCharsets.UTF_8);
            return "redirect:/error-auth?err=" + msg;
        }

        return "redirect:main";
    }
}
