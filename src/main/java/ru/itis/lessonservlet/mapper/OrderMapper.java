package ru.itis.lessonservlet.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.model.OrdersEntity;


public interface OrderMapper extends RowMapper<OrdersEntity> {

    OrdersEntity toEntity(OrderRequest request);
}
