package ru.itis.lessonservlet.mapper.impl;



import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.mapper.OrderMapper;
import ru.itis.lessonservlet.model.OrdersEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapperImpl implements OrderMapper {
    @Override
    public OrdersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrdersEntity.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .productId(rs.getLong("product_id"))
                .orderDate(rs.getTimestamp("order_date").toLocalDateTime())
                .statusCode(rs.getString("status_code"))
                .build();
    }

    @Override
    public OrdersEntity toEntity(OrderRequest request) {
        return OrdersEntity.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .orderDate(request.getOrderDate())
                .statusCode(request.getStatusCode())
                .build();
    }
}
