package ru.itis.lessonservlet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.model.FavouriteEntity;
import ru.itis.lessonservlet.model.ProductEntity;

import java.util.List;
import java.util.Optional;


public interface FavouritesRepository extends JpaRepository<FavouriteEntity, Integer> {

    @Query("SELECT f FROM FavouriteEntity f WHERE f.user.id = ?1 AND f.product.id = ?2")
    Optional<FavouriteEntity> findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT f.product FROM FavouriteEntity f WHERE f.user.id = ?1")
    List<ProductEntity> findFavouritesByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FavouriteEntity f WHERE f.user.id = ?1 AND f.product.id = ?2")
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
