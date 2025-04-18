package ru.itis.lessonservlet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.lessonservlet.entity.FavouriteEntity;
import ru.itis.lessonservlet.entity.ProductEntity;

import java.util.List;
import java.util.Optional;


public interface FavouritesRepository extends JpaRepository<FavouriteEntity, Integer> {

    @Query("SELECT f FROM FavouriteEntity f WHERE f.user.id = :userId AND f.product.id = :productId")
    Optional<FavouriteEntity> findByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);

    @Query("SELECT f.product FROM FavouriteEntity f WHERE f.user.id = :userId")
    List<ProductEntity> findFavouritesByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FavouriteEntity f WHERE f.user.id = :userId AND f.product.id = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);
}
