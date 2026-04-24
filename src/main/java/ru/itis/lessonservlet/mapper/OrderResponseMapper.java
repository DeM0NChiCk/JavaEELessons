package ru.itis.lessonservlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.lessonservlet.dto.response.OrdersAllResponse;
import ru.itis.lessonservlet.dto.response.OrdersResponse;
import ru.itis.lessonservlet.entity.OrdersEntity;
import ru.itis.lessonservlet.utils.OrderUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderResponseMapper {
    @Mapping(source = "items", target = "orderItems")
    @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "localDateTimeToDate")
    OrdersResponse toResponse(OrdersEntity entity);

    List<OrdersResponse> toResponseList(List<OrdersEntity> entities);

    @Mapping(source = "items", target = "orderItems")
    @Mapping(source = "orderDate", target = "orderDate", qualifiedByName = "localDateTimeToDate")
    OrdersAllResponse toResponseAll(OrdersEntity entity);

    List<OrdersAllResponse> toResponseAllList(List<OrdersEntity> entities);

    @Named("localDateTimeToDate")
    static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return OrderUtils.mapLocalDateTimeToDate(localDateTime);
    }
}
