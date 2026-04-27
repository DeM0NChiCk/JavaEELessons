package ru.itis.semestr.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewProductRequest {

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    private String description;

    @Min(value = 0, message = "Цена должна быть положительной")
    private double price;

    @Min(value = 0, message = "Количество не может быть отрицательным")
    private int quantity;

    private byte[] image;
}