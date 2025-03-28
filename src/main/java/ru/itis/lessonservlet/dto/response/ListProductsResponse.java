package ru.itis.lessonservlet.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListProductsResponse {

    List<ProductResponse> products;

}