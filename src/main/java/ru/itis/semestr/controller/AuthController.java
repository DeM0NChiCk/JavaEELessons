package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.semestr.dto.request.SignUpRequest;
import ru.itis.semestr.dto.response.AuthResponse;
import ru.itis.semestr.service.UserService;

import static ru.itis.semestr.entity.UserEntity.USER_ROLE;

@Hidden
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
