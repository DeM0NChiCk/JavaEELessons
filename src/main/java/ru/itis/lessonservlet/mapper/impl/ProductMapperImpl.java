package ru.itis.lessonservlet.mapper.impl;

import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.ProductResponse;
import ru.itis.lessonservlet.mapper.ProductMapper;
import ru.itis.lessonservlet.model.ProductEntity;
import ru.itis.lessonservlet.utils.ImageUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductEntity toEntity(NewProductRequest request) {
        return ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .image(request.getImage())
                .build();
    }

    @Override
    public ListProductsResponse toDto(List<ProductEntity> entity) {
        List<ProductResponse> productResponses = entity.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .image(ImageUtils.encodeToBase64(product.getImage()))
                        .quantity(product.getQuantity())
                        .category(product.getCategories())
                        .isFavorite(product.isFavorite())
                        .build())
                .collect(Collectors.toList());

        return ListProductsResponse.builder()
                .products(productResponses)
                .build();
    }

    @Override
    public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .quantity(rs.getInt("quantity"))
                .image(rs.getBytes("image"))
                .build();
    }
}
