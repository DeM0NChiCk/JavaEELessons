package ru.itis.semestr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semestr.dto.response.ListCategoriesResponse;
import ru.itis.semestr.entity.CategoryEntity;
import ru.itis.semestr.mapper.CategoryMapper;
import ru.itis.semestr.repository.CategoryRepository;
import ru.itis.semestr.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ListCategoriesResponse getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }
}
