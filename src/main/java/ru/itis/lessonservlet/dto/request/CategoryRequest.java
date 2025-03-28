package ru.itis.lessonservlet.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String name;
}
