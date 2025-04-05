package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;

public interface UserService {
    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);

    AuthResponse checkAdmin(UserDataResponse user);
}
