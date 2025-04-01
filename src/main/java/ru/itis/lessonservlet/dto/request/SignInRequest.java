package ru.itis.lessonservlet.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;

    private String password;

}
