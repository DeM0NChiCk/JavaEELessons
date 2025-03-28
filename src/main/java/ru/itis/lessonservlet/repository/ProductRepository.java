package ru.itis.lessonservlet.repository;

import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<ProductEntity> getAllProducts();
    Optional<ProductEntity> findProductById(Long id);
    Optional<ProductEntity> saveNewProduct(ProductEntity product, List<CategoryRequest> category);
}
