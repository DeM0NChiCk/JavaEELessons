package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    @Override
    public ListProductsResponse getAllProducts() {
        List<ProductEntity> products = productRepository.getAllProducts();
        log.info("Get all products");

        if (products.isEmpty()) {
            return new ListProductsResponse(Collections.emptyList());
        }
        return productMapper.toDto(products);
    }

    @Override
    public void saveNewProduct(NewProductRequest request, List<CategoryRequest> requestList) {
        Optional<ProductEntity> optionalProduct = productRepository.saveNewProduct(productMapper.toEntity(request), requestList);

    }
}
