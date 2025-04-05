package ru.itis.lessonservlet.repository;


import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;

public interface FavouritesRepository {

    void addToFavourites(Long userId, Long productId);

    void removeFromFavourites(Long userId, Long productId);

    List<ProductEntity> getFavouritesByUser(Long userId);

    boolean isProductInFavourites(Long userId, Long productId);
}
