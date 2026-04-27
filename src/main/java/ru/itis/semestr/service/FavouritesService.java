package ru.itis.semestr.service;

import ru.itis.semestr.dto.response.ListProductsResponse;

public interface FavouritesService {

    ListProductsResponse getAllFavourites(Long userId);

    void deleteFavourite(Long userId, Long productId);

    void addFavourite(Long userId, Long productId);

}
