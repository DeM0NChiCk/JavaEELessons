package ru.itis.lessonservlet.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductApiResponse {

    private Long id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String image;

}