package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.response.CategoryResponse;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.model.CategoryEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper  {
    CategoryEntity toEntity(CategoryRequest dto);

    CategoryResponse toDto(CategoryEntity entity);

    List<CategoryResponse> toDtoList(List<CategoryEntity> entities);

    ListCategoriesResponse toDto(List<CategoryEntity> entity);
}
