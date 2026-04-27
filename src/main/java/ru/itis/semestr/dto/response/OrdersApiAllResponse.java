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
public class OrdersApiAllResponse {

    private int status;

    private String statusDesc;

    private List<OrdersAllResponse> list;

}
