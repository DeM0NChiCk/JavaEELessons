package ru.itis.lessonservlet.service;


import ru.itis.lessonservlet.dto.response.ListProductsResponse;

public interface FavouritesService {

    ListProductsResponse getAllFavorites(Long userId);

    void deleteFavorite(Long userId, Long productId);

    void addFavorite(Long userId, Long productId);
}
