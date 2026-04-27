package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.semestr.dto.response.*;
import ru.itis.semestr.service.OrdersService;
import ru.itis.semestr.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Управление заказами и заказанными товарами")
@SecurityRequirement(name = "bearerAuth")
public class OrderApiController {

    private final OrdersService ordersService;
    private final ProductService productService;

    @Operation(summary = "Получение заказов по ID пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiListResponse.class),
                            examples = @ExampleObject(
                                    name = "Успешный ответ",
                                    value = """
                {
                  "status": 200,
                  "statusDesc": "Список заказов успешно получен",
                  "list": [
                    {
                      "orderDate": "2025-06-05T03:31:15.732Z",
                      "orderItems": [
                        {
                          "product": {
                            "id": 1,
                            "name": "Ноутбук",
                            "description": "Игровой ноутбук с RTX 3080",
                            "price": 1500.99,
                            "quantity": 5,
                            "image": "https://example.com/images/laptop.png",
                            "category": [
                              {
                                "id": 10,
                                "name": "Электроника"
                              }
                            ],
                            "favorite": true
                          },
                          "quantity": 1
                        }
                      ],
                      "user": {
                        "id": 123,
                        "email": "user@example.com",
                        "username": "user123",
                        "role": "user"
                      },
                      "orderNumber": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                      "statusCode": "pending"
                    }
                  ]
                }
                """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiListResponse.class),
                            examples = @ExampleObject(
                                    name = "Пользователь не найден",
                                    value = """
                {
                  "status": 404,
                  "statusDesc": "Пользователь не найден",
                  "list": []
                }
                """
                            )
                    )
            ),
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<OrdersApiListResponse> getOrdersByUserId(@PathVariable Long userId) {
        List<OrdersResponse> responses = ordersService.getOrdersByUserId(userId);

        if (responses == null || responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OrdersApiListResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .statusDesc("Пользователь не найден")
                            .list(Collections.emptyList())
                            .build());
        }

        return ResponseEntity.ok(
                OrdersApiListResponse.builder()
                        .status(HttpStatus.OK.value())
                        .statusDesc("Список заказов успешно получен")
                        .list(responses)
                        .build());
    }

    @Operation(summary = "Получение всех заказов (только для админа)")
    @ApiResponse(responseCode = "200", description = "Список всех заказов",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrdersApiAllResponse.class),
                    examples = @ExampleObject(
                            name = "Успешный ответ",
                            value = """
                                    {
                                      "status": 200,
                                      "statusDesc": "Список заказов успешно получен",
                                      "list": [
                                        {
                                          "orderDate": "2025-06-05T03:31:15.732Z",
                                          "orderNumber": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                          "statusCode": "pending",
                                          "user": {
                                            "id": 123,
                                            "email": "user@example.com",
                                            "username": "user123",
                                            "role": "user"
                                          }
                                        }
                                      ]
                                    }
                                    """
                    )
            ))
    @GetMapping
    public ResponseEntity<OrdersApiAllResponse> getAllOrders() {
        List<OrdersAllResponse> responses = ordersService.getAllOrders();

        if (responses == null || responses.isEmpty()) {
            return ResponseEntity.ok(
                    OrdersApiAllResponse.builder()
                            .status(HttpStatus.OK.value())
                            .statusDesc("Список заказов пуст")
                            .list(Collections.emptyList())
                            .build()
            );
        }

        return ResponseEntity.ok(
                OrdersApiAllResponse.builder()
                        .status(HttpStatus.OK.value())
                        .statusDesc("Список заказов успешно получен")
                        .list(responses)
                        .build()
        );
    }

    @Operation(summary = "Обновление статуса заказа по номеру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус обновлён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiUpdateStatus.class),
                            examples = @ExampleObject(
                                    name = "Успешное обновление",
                                    value = """
                                            {
                                              "status": 200,
                                              "statusDesc": "Статус обновлён"
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiUpdateStatus.class),
                            examples = @ExampleObject(
                                    name = "Заказ не найден",
                                    value = """
                                            {
                                              "status": 404,
                                              "statusDesc": "Заказ не найден"
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "500", description = "Ошибка при обновлении статуса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiUpdateStatus.class),
                            examples = @ExampleObject(
                                    name = "Ошибка сервера",
                                    value = """
                                            {
                                              "status": 400,
                                              "statusDesc": "Ошибка при обновлении статуса"
                                            }
                                            """
                            )
                    ))
    })
    @PutMapping("/status")
    public ResponseEntity<OrdersApiUpdateStatus> updateOrderStatus(
            @RequestParam UUID orderNumber,
            @RequestParam String statusCode)
    {
        try {
            Optional<OrdersResponse> optionalOrder = ordersService.findByOrderNumber(orderNumber);
            if (optionalOrder.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                OrdersApiUpdateStatus.builder()
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .statusDesc("Заказ не найден")
                                        .build());
            }

            // Обновление
            ordersService.updateOrderStatus(orderNumber, statusCode);

            return ResponseEntity.ok(
                    OrdersApiUpdateStatus.builder()
                            .status(HttpStatus.OK.value())
                            .statusDesc("Статус обновлён")
                            .build()
            );

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(OrdersApiUpdateStatus.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusDesc("Ошибка при обновлении статуса")
                            .build());
        }
    }

    @Operation(summary = "Поиск заказа по номеру заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Заказ найден",
                                    value = """
                                            {
                                              "status": 200,
                                              "statusDesc": "Заказ найден",
                                              "order": {
                                                "orderDate": "2025-06-05T03:31:15.732Z",
                                                "orderItems": [
                                                  {
                                                    "product": {
                                                      "id": 1,
                                                      "name": "Ноутбук",
                                                      "description": "Игровой ноутбук с RTX 3080",
                                                      "price": 1500.99,
                                                      "quantity": 5,
                                                      "image": "https://example.com/images/laptop.png",
                                                      "category": [
                                                        {
                                                          "id": 10,
                                                          "name": "Электроника"
                                                        }
                                                      ],
                                                      "favorite": true
                                                    },
                                                    "quantity": 1
                                                  }
                                                ],
                                                "user": {
                                                  "id": 123,
                                                  "email": "user@example.com",
                                                  "username": "user123",
                                                  "role": "user"
                                                },
                                                "orderNumber": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                                "statusCode": "pending"
                                              }
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiResponse.class),
                            examples = @ExampleObject(
                                    name = "Заказ не найден",
                                    value = """
                                            {
                                              "status": 404,
                                              "statusDesc": "Заказ не найден",
                                              "order": null
                                            }
                                            """
                            )
                    ))
    })
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrdersApiResponse> findByOrderNumber(@PathVariable UUID orderNumber) {
        return ordersService.findByOrderNumber(orderNumber)
                .map(ordersResponse ->
                        ResponseEntity.ok(
                                OrdersApiResponse.builder()
                                        .status(HttpStatus.OK.value())
                                        .statusDesc("Заказ найден")
                                        .order(ordersResponse)
                                        .build()
                        ))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(OrdersApiResponse.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .statusDesc("Заказ не найден")
                                .order(null)
                                .build()));
    }

    @Operation(summary = "Получение всех заказанных товаров пользователем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказанных товаров",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiProductResponse.class),
                            examples = @ExampleObject(
                                    name = "Список заказанных товаров",
                                    value = """
                                            {
                                              "status": 200,
                                              "statusDesc": "Список заказанных товаров",
                                              "listProducts": {
                                                "products": [
                                                  {
                                                    "id": 1,
                                                    "name": "Наушники",
                                                    "description": "Беспроводные наушники",
                                                    "price": 99.99,
                                                    "quantity": 10,
                                                    "image": "https://example.com/images/headphones.png",
                                                    "category": [
                                                      {
                                                        "id": 20,
                                                        "name": "Аудио"
                                                      }
                                                    ],
                                                    "favorite": false
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Список заказанных товаров для пользователя не найден или пуст",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdersApiProductResponse.class),
                            examples = @ExampleObject(
                                    name = "Пустой список товаров",
                                    value = """
                                            {
                                              "status": 404,
                                              "statusDesc": "Список заказанных товаров для пользователя не найден или пуст",
                                              "listProducts": null
                                            }
                                            """
                            )
                    ))
    })
    @ApiResponse(responseCode = "200", description = "Список заказанных товаров")
    @GetMapping("/products/user/{userId}")
    public ResponseEntity<OrdersApiProductResponse> getOrderedProductsByUserId(@PathVariable Long userId) {
        ListProductsResponse response = productService.getOrderedProductsByUserId(userId);

        if (response == null || response.getProducts() == null || response.getProducts().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OrdersApiProductResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .statusDesc("Список заказанных товаров для пользователя не найден или пуст")
                            .listProducts(null)
                            .build()
                    );
        }

        return ResponseEntity.ok(
                OrdersApiProductResponse.builder()
                        .status(HttpStatus.OK.value())
                        .statusDesc("Список заказанных товаров")
                        .listProducts(response)
                        .build()
        );
    }
}
