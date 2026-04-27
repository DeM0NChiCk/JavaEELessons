package ru.itis.semestr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.semestr.dto.request.CategoryRequest;
import ru.itis.semestr.dto.request.NewProductRequest;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.dto.response.ProductResponse;
import ru.itis.semestr.entity.CategoryEntity;
import ru.itis.semestr.entity.ProductEntity;
import ru.itis.semestr.mapper.CategoryMapper;
import ru.itis.semestr.mapper.ProductMapper;
import ru.itis.semestr.repository.CategoryRepository;
import ru.itis.semestr.repository.FavouritesRepository;
import ru.itis.semestr.repository.ProductRepository;
import ru.itis.semestr.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

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

        ListProductsResponse response = productMapper.toDto(products);

        for (ProductResponse product : response.getProducts()) {
            product.setCategory(categoryRepository.findCategoriesByProductId(product.getId()));

            product.setFavorite(
                    favouritesRepository.findByUserIdAndProductId(
                            userId,
                            product.getId()
                    ).isPresent()
            );
        }

        return response;
    }

    @Transactional
    @Override
    public void saveNewProduct(NewProductRequest request, List<CategoryRequest> requestList) {
        ProductEntity product = productMapper.toEntity(request);

        List<CategoryEntity> categoryEntities = requestList.stream()
                .map(r -> categoryRepository.findByName(r.getName())
                        .orElseGet(() -> categoryRepository.save(
                                categoryMapper.toEntity(r)
                        )))
                .collect(Collectors.toList());

        product.setCategories(categoryEntities);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void updateProduct(Long id, String name, String description, double price, int quantity, byte[] image) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        if (image != null) {
            product.setImage(image);
        }

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ListProductsResponse getOrderedProductsByUserId(Long userId) {
        List<ProductEntity> orderedProducts = productRepository.findOrderedProductsByUserId(userId);

        List<ProductResponse> productResponses = productMapper.toDtoList(orderedProducts);

        for (ProductResponse product : productResponses) {
            product.setCategory(categoryRepository.findCategoriesByProductId(product.getId()));
            product.setFavorite(
                    favouritesRepository.findByUserIdAndProductId(
                            userId,
                            product.getId()
                    ).isPresent()
            );
        }

        return ListProductsResponse.builder()
                .products(productResponses)
                .build();
    }
}
