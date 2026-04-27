package ru.itis.semestr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.semestr.dto.request.CategoryRequest;
import ru.itis.semestr.dto.response.CategoryResponse;
import ru.itis.semestr.dto.response.ListCategoriesResponse;
import ru.itis.semestr.entity.CategoryEntity;

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
