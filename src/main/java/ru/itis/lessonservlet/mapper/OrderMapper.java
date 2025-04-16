package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.model.OrdersEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "statusCode", ignore = true)
    OrdersEntity toEntity(OrderRequest request);


}
