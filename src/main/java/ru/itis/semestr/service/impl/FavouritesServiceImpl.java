package ru.itis.semestr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.entity.FavouriteEntity;
import ru.itis.semestr.entity.ProductEntity;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.mapper.ProductMapper;
import ru.itis.semestr.repository.FavouritesRepository;
import ru.itis.semestr.service.FavouritesService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavouritesServiceImpl implements FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final ProductMapper productMapper;

    @Override
    public ListProductsResponse getAllFavourites(Long userId) {
        List<ProductEntity> products = favouritesRepository.findFavouritesByUserId(userId);
        products.forEach(p -> p.setFavorite(true));
        return productMapper.toDto(products);
    }

    @Transactional
    @Override
    public void deleteFavourite(Long userId, Long productId) {
        favouritesRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    @Override
    public void addFavourite(Long userId, Long productId) {
        boolean exists = favouritesRepository.findByUserIdAndProductId(userId, productId).isPresent();
        if (!exists) {
            FavouriteEntity favourite = FavouriteEntity.builder()
                    .user(UserEntity.builder().id(userId).build())
                    .product(ProductEntity.builder().id(productId).build())
                    .build();
            favouritesRepository.save(favourite);
        }
    }
}
