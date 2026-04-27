package ru.itis.semestr.dto.response;

import lombok.*;

@Data
@Builder
@ToString
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
