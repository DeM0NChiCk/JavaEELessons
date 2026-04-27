package ru.itis.semestr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class OrdersApiProductResponse {

    private int status;

    private String statusDesc;

    private ListProductsResponse listProducts;

}
