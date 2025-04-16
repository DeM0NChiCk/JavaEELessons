package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.mapper.CategoryMapper;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.CategoryEntity;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.repository.FavouritesRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FavouritesRepository favouritesRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;


    @Override
    public ListProductsResponse getAllProducts(Long userId) {
        List<ProductEntity> products = productRepository.findAllWithCategories();

        // enrich: добавить категории и isFavorite
        for (ProductEntity product : products) {
            product.setFavorite(
                    favouritesRepository.findByUserIdAndProductId(
                            userId,
                            product.getId()
                    ).isPresent()
            );
        }

        log.info("Get all products");

        return productMapper.toDto(products);
    }

    @Transactional
    @Override
    public void saveNewProduct(NewProductRequest request, List<CategoryRequest> requestList) {
        ProductEntity product = productMapper.toEntity(request);

        // обработка категорий
        Set<CategoryEntity> categoryEntities = requestList.stream()
                .map(r -> categoryRepository.findByName(r.getName())
                        .orElseGet(() -> categoryRepository.save(
                                categoryMapper.toEntity(r)
                        )))
                .collect(Collectors.toSet());

        product.setCategories(categoryEntities);
        productRepository.save(product);
    }
}
