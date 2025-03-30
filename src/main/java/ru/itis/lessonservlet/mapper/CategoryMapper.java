package ru.itis.lessonservlet.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.model.CategoryEntity;


import java.util.List;

public interface CategoryMapper extends RowMapper<CategoryEntity> {
    ListCategoriesResponse toDto(List<CategoryEntity> entity);
}
