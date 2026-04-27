package ru.itis.semestr.service;

import ru.itis.semestr.dto.request.SignUpRequest;
import ru.itis.semestr.dto.response.AuthResponse;

public interface UserService {

    AuthResponse signUp(SignUpRequest request);

}
