package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.lessonservlet.dto.request.SignUpRequest;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.entity.UserEntity;
import ru.itis.lessonservlet.utils.AuthUtils;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashPassword", source = "password", qualifiedByName = "hashPassword")
    UserEntity toEntity(SignUpRequest request);

    UserDataResponse toDto(UserEntity entity);

    @Named("hashPassword")
    static String hashPassword(String password) {
        return AuthUtils.hashPassword(password);
    }
}
