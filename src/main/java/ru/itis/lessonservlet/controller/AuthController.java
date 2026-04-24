package ru.itis.lessonservlet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.service.UserService;

import static ru.itis.lessonservlet.entity.UserEntity.USER_ROLE;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "signIn";
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
                .role(USER_ROLE)
                .build();

        AuthResponse authResponse = userService.signUp(signUpRequest);

        if (authResponse.getStatus() == 0) {
            return "redirect:signIn";
        } else {
            return "redirect:error-auth?err=" + authResponse.getStatusDesc();
        }
    }
}
