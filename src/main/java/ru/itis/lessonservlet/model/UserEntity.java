package ru.itis.lessonservlet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    public final static String USER_ROLE = "user";
    public final static String ADMIN_ROLE = "admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Pattern(regexp = "admin|user", message = "Invalid role")
    private String role;
}
