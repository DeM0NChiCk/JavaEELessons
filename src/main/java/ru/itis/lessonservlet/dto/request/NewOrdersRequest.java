package ru.itis.lessonservlet.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewOrdersRequest {
    private LocalDateTime orderDate;
    private String statusCode;
    private List<NewOrderItemRequest> items;
}