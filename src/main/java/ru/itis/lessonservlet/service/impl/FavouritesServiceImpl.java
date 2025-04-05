package ru.itis.lessonservlet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.repository.FavouritesRepository;
import ru.itis.lessonservlet.service.FavouritesService;


import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FavouritesServiceImpl implements FavouritesService {

    private final FavouritesRepository favouritesRepository;

    private final ProductMapper productMapper;

    @Override
    public ListProductsResponse getAllFavorites(Long userId) {
        List<ProductEntity> products = favouritesRepository.getFavouritesByUser(userId);
        if (products.isEmpty()) {
            return new ListProductsResponse(Collections.emptyList());
        }
        return productMapper.toDto(products);
    }

    @Override
    public void deleteFavorite(Long userId, Long productId) {
        favouritesRepository.removeFromFavourites(userId, productId);
    }

    @Override
    public void addFavorite(Long userId, Long productId) {
        favouritesRepository.addToFavourites(userId, productId);
    }
}
