package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.semestr.dto.request.SignInRequest;
import ru.itis.semestr.dto.response.AuthApiResponse;
import ru.itis.semestr.service.impl.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Методы для входа в систему")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(summary = "Вход администратора по email и паролю",
            description = "Возвращает JWT токен при успешной аутентификации администратора"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Успешный вход",
                                    summary = "Успешный JWT",
                                    value = """
                                                {
                                                    "status": 200,
                                                    "statusDesc": "Добро пожаловать!",
                                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
                                                }
                                            """
                            ))),
            @ApiResponse(responseCode = "403", description = "Не администратор",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Не администратор",
                                    summary = "Доступ запрещен",
                                    value = """
                                                {
                                                    "status": 403,
                                                    "statusDesc": "Доступ запрещен: не администратор",
                                                    "token": null
                                                }
                                            """
                            ))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Неверный логин",
                                    summary = "Ошибка авторизации",
                                    value = """
                                                {
                                                    "status": 401,
                                                    "statusDesc": "Неверные учетные данные",
                                                    "token": null
                                                }
                                            """
                            )))
    })
    @PostMapping("/signIn")
    public ResponseEntity<AuthApiResponse> signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для входа",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignInRequest.class),
                            examples = @ExampleObject(
                                    name = "Пример запроса",
                                    value = """
                                                {
                                                    "email": "admin@example.com",
                                                    "password": "admin123"
                                                }
                                            """
                            )
                    )
            )
            @Valid @RequestBody SignInRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_admin"));

            AuthApiResponse response;

            if (!isAdmin) {
                response = AuthApiResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .statusDesc("Доступ запрещен: не администратор")
                        .token(null)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            String jwt = jwtService.generateToken(userDetails);

            response = AuthApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .statusDesc("Добро пожаловать!")
                    .token(jwt)
                    .build();

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthApiResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .statusDesc("Неверные учетные данные")
                            .token(null)
                            .build());
        }

    }
}
