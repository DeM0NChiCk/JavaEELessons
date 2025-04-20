package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ListCategoriesResponse getAllCategories();

    Integer addCategoryAndGetId(String name);

    Optional<Integer> findCategoryByName(String name);

    List<CategoryEntity> findCategoriesByProductId(Long productId);

    Optional<CategoryEntity> findCategoryById(Long id);

    void saveProductCategories(Long productId, List<CategoryRequest> requests);
}
