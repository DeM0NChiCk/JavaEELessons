package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(SignUpRequest request);

    UserDataResponse toDto(UserEntity entity);
}
