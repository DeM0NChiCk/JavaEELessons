package ru.itis.lessonservlet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.service.UserService;

@Controller
@RequestMapping("")
public class AuthController {

    private final UserService userService;

    private final String AUTHORIZATION;

    private final String IS_ADMIN;

    public AuthController(
            UserService userService,
            @Qualifier("IS_ADMIN") String isAdmin,
            @Qualifier("AUTHORIZATION") String authorization
    ) {
        this.userService = userService;
        this.AUTHORIZATION = authorization;
        IS_ADMIN = isAdmin;
    }

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "signIn"; // jsp/signIn.jsp, если настроен viewResolver
    }

    @PostMapping("/signIn")
    public String signIn(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletRequest request) {

        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        AuthResponse authResponse = userService.signIn(signInRequest);

        if (authResponse.getStatus() == 0) {
            HttpSession session = request.getSession(true);
            session.setAttribute(AUTHORIZATION, true);
            session.setAttribute(IS_ADMIN, false);
            session.setAttribute("user", authResponse.getUser());

            return "redirect:/main";
        } else {
            return "redirect:/error?err=" + authResponse.getStatusDesc();
        }
    }

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email(email)
                .username(username)
                .password(password)
                .role("USER")
                .build();

        AuthResponse authResponse = userService.signUp(signUpRequest);

        if (authResponse.getStatus() == 0) {
            return "redirect:/signIn";
        } else {
            return "redirect:/error?err=" + authResponse.getStatusDesc();
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute(AUTHORIZATION, false);
        session.setAttribute(IS_ADMIN, false);
        session.invalidate();
        return "redirect:/";
    }
}
