package ru.itis.lessonservlet.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    public final static String USER_ROLE = "user";
    public final static String ADMIN_ROLE = "admin";

    private Long id;

    private String email;

    private String hashPassword;

    private String username;

    private String role;
}
