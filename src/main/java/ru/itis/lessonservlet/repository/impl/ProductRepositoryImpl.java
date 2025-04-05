package ru.itis.lessonservlet.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.mapper.impl.ProductMapperImpl;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.repository.CategoryRepository;
import ru.itis.lessonservlet.repository.FavouritesRepository;
import ru.itis.lessonservlet.repository.ProductRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select * from products where id = ?";

    private static final String SQL_SELECT_ALL_PRODUCTS = "select * from products";

    private static final String SQL_INSERT_PRODUCT = "insert into products(name, description, price, quantity,image) values (?,?,?,?,?);";

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    private final FavouritesRepository favouritesRepository;


    @Override
    public List<ProductEntity> getAllProducts(Long userId) {
        List<ProductEntity> products = jdbcTemplate.query(SQL_SELECT_ALL_PRODUCTS, productMapper);

        for (ProductEntity product : products) {
            product.setCategories(categoryRepository.findCategoriesByProductId(product.getId()));
            product.setFavorite(favouritesRepository.isProductInFavourites(userId, product.getId()));
        }

        return products;
    }

    @Override
    public Optional<ProductEntity> findProductById(Long id) {
        try {
            ProductEntity product = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, productMapper, id);
            if (product != null) {
                product.setCategories(categoryRepository.findCategoriesByProductId(id));
            }

            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductEntity> saveNewProduct(ProductEntity product, List<CategoryRequest> category) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_PRODUCT, new String[] {"id"});
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setDouble(3, product.getPrice());
                ps.setInt(4, product.getQuantity());
                ps.setBytes(5, product.getImage());
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();

            if (product.getCategories() == null) {
                categoryRepository.saveProductCategories(id, category);
            }


            return findProductById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
