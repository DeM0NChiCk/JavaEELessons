package ru.itis.lessonservlet.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.model.UserEntity;

public interface UserMapper extends RowMapper<UserEntity> {

    UserEntity toEntity(SignUpRequest request);

    UserDataResponse toDto(UserEntity entity);
}
