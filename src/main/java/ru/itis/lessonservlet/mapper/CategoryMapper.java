package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.response.CategoryResponse;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.entity.CategoryEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper  {
    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryRequest dto);

    CategoryResponse toDto(CategoryEntity entity);

    List<CategoryResponse> toDtoList(List<CategoryEntity> entities);

    default ListCategoriesResponse toDto(List<CategoryEntity> entities) {
        return ListCategoriesResponse.builder()
                .categories(toDtoList(entities))
                .build();
    }
}
