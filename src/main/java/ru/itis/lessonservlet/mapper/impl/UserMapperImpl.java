package ru.itis.lessonservlet.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.mapper.UserMapper;
import ru.itis.lessonservlet.model.UserEntity;
import ru.itis.lessonservlet.utils.AuthUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(SignUpRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .hashPassword(AuthUtils.hashPassword(request.getPassword()))
                .role(request.getRole())
                .build();
    }

    @Override
    public UserDataResponse toDto(UserEntity entity) {
        return UserDataResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .role(entity.getRole())
                .build();
    }

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .username(rs.getString("username"))
                .hashPassword(rs.getString("hash_password"))
                .role(rs.getString("role"))
                .build();
    }
}
