package ru.itis.lessonservlet.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.repository.FavouritesRepository;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FavouritesRepositoryImpl implements FavouritesRepository {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_INTO_FAVORITE = "insert into favourites (user_id, product_id) values (?, ?)";

    private final static String SELECT_FROM_FAVORITE = "select * from favourites";

    private final static String DELETE_FROM_FAVORITE = "delete from favourites WHERE user_id = ? and product_id = ?";

    private final static String SELECT_FROM_FAVORITE_BY_ID_USER = "select p.id, p.name, p.description, p.price, p.quantity, p.image " +
            "from products p " +
            "join favourites f ON p.id = f.product_id " +
            "where f.user_id = ?";

    private final static String SELECT_IS_PRODUCT_IN_FAVORITE = "select count(*) from favourites where user_id = ? and product_id = ?";

    private final ProductMapper productMapper ;

    private final CategoryRepository categoryRepository;


    @Override
    public void addToFavourites(Long userId, Long productId) {
        if (!isProductInFavourites(userId, productId)) {
            jdbcTemplate.update(INSERT_INTO_FAVORITE, userId, productId);
        }
    }

    @Override
    public void removeFromFavourites(Long userId, Long productId) {
        jdbcTemplate.update(DELETE_FROM_FAVORITE, userId, productId);
    }

    @Override
    public List<ProductEntity> getFavouritesByUser(Long userId) {
        List<ProductEntity> products = jdbcTemplate.query(SELECT_FROM_FAVORITE_BY_ID_USER, productMapper, userId);
        for (ProductEntity product : products) {
            product.setCategories(categoryRepository.findCategoriesByProductId(product.getId()));
            product.setFavorite(isProductInFavourites(userId, product.getId()));
        }
        return products;
    }

    @Override
    public boolean isProductInFavourites(Long userId, Long productId) {
        Integer count = jdbcTemplate.queryForObject(SELECT_IS_PRODUCT_IN_FAVORITE, new Object[]{userId, productId}, Integer.class);
        return count > 0;
    }
}
