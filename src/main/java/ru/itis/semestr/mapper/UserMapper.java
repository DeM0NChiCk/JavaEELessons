package ru.itis.semestr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.semestr.dto.request.SignUpRequest;
import ru.itis.semestr.dto.response.UserDataResponse;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.utils.AuthUtils;

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
