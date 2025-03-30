package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.model.CategoryEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.service.CategoryService;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public ListCategoriesResponse getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.getAllCategories();
        if (categories.isEmpty()) {
            return new ListCategoriesResponse(Collections.emptyList());
        }
        return categoryMapper.toDto(categories);
    }
}
