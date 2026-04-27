package ru.itis.semestr.entity;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private String title;
    private String summary;
    private String details;
}
