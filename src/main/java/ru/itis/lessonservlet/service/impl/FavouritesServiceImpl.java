package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.FavouriteEntity;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.model.UserEntity;
import ru.itis.lessonservlet.repository.FavouritesRepository;
import ru.itis.lessonservlet.repository.ProductRepository;
import ru.itis.lessonservlet.service.FavouritesService;


import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FavouritesServiceImpl implements FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final ProductMapper productMapper;

    @Override
    public ListProductsResponse getAllFavorites(Long userId) {
        List<ProductEntity> products = favouritesRepository.findFavouritesByUserId(userId);
        products.forEach(p -> p.setFavorite(true));
        return productMapper.toDto(products);
    }

    @Transactional
    @Override
    public void deleteFavorite(Long userId, Long productId) {
        favouritesRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    @Override
    public void addFavorite(Long userId, Long productId) {
        boolean exists = favouritesRepository.findByUserIdAndProductId(userId, productId).isPresent();
        if (!exists) {
            FavouriteEntity favourite = FavouriteEntity.builder()
                    .user(UserEntity.builder().id(userId).build()) // заглушка без загрузки всей сущности
                    .product(ProductEntity.builder().id(productId).build())
                    .build();
            favouritesRepository.save(favourite);
        }
    }
}
