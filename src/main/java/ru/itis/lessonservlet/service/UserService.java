package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;

public interface UserService {
    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);
}
