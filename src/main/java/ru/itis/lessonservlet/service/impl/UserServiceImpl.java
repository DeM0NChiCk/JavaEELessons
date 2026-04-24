package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.mapper.UserMapper;
import ru.itis.lessonservlet.entity.UserEntity;
import ru.itis.lessonservlet.repository.UserRepository;
import ru.itis.lessonservlet.service.UserService;
import ru.itis.lessonservlet.utils.AuthUtils;

import java.util.Optional;

import static ru.itis.lessonservlet.entity.UserEntity.ADMIN_ROLE;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        if(request.getEmail() == null || request.getEmail().isBlank())
            return response(1, "Пустой адрес электронной почты", null);

        if(request.getPassword() == null || request.getPassword().isBlank())
            return response(2, "Пустой пароль", null);

        if(request.getUsername() == null || request.getUsername().isBlank())
            return response(3, "Пустое имя пользователя", null);

        if(!AuthUtils.checkEmail(request.getEmail()))
            return response(4, "Неверный адрес электронной почты", null);

        if(!AuthUtils.checkPassword(request.getPassword()))
            return response(5, "Слабый пароль", null);

        if(userRepository.findUserByEmail(request.getEmail()).isPresent())
            return response(6, "Адрес электронный почты уже использован", null);

        if(userRepository.findUserByUsername(request.getUsername()).isPresent())
            return response(7, "Имя пользователя занято", null);

        UserEntity user = userRepository.save(userMapper.toEntity(request));

        return response(0, "OK", userMapper.toDto(user));
    }

    private AuthResponse response(int status, String statusDesc, UserDataResponse user) {
        return AuthResponse.builder()
                .status(status)
                .statusDesc(statusDesc)
                .user(user)
                .build();
    }
}