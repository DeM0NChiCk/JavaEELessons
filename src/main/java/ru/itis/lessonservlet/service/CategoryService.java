package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;

public interface CategoryService {
    ListCategoriesResponse getAllCategories();
}
