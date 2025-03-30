package ru.itis.lessonservlet.mapper.impl;

import ru.itis.lessonservlet.dto.response.CategoryResponse;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.model.CategoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CategoryEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public ListCategoriesResponse toDto(List<CategoryEntity> entity) {
        List<CategoryResponse> categoryResponses = entity.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());

        return ListCategoriesResponse.builder()
                .categories(categoryResponses)
                .build();
    }
}
