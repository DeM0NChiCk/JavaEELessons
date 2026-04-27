package ru.itis.semestr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
public class OrdersApiListResponse {

    private int status;

    private String statusDesc;

    private List<OrdersResponse> list;

}
