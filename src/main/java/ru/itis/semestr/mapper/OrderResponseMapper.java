package ru.itis.semestr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itis.semestr.dto.response.OrdersAllResponse;
import ru.itis.semestr.dto.response.OrdersResponse;
import ru.itis.semestr.entity.OrdersEntity;
import ru.itis.semestr.utils.OrderUtils;

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
