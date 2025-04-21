package ru.itis.lessonservlet.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.entity.CategoryEntity;
import ru.itis.lessonservlet.entity.ProductEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ListCategoriesResponse getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories); // маппер сам обработает пустой список
    }

    @Override
    @Transactional
    public Integer addCategoryAndGetId(String name) {
        CategoryEntity saved = categoryRepository.save(new CategoryEntity(null, name));
        return saved.getId().intValue();
    }

    @Override
    public Optional<Integer> findCategoryByName(String name) {
        return categoryRepository.findCategoryIdByName(name);
    }

    @Override
    public List<CategoryEntity> findCategoriesByProductId(Long productId) {
        return categoryRepository.findCategoriesByProductId(productId);
    }

    @Override
    public Optional<CategoryEntity> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    @Override
    public void saveProductCategories(Long productId, List<CategoryRequest> requests) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        for (CategoryRequest request : requests) {
            CategoryEntity category = categoryRepository.findByName(request.getName())
                    .orElseGet(() -> categoryRepository.save(
                            categoryMapper.toEntity(request)));

            product.getCategories().add(category);
        }

        productRepository.save(product);
    }
}
