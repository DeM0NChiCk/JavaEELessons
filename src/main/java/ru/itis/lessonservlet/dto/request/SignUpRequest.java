package ru.itis.lessonservlet.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email;

    private String password;

    private String username;

    private String role;
}
