package ru.itis.lessonservlet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.lessonservlet.config.security.UserPrincipal;
import ru.itis.lessonservlet.dto.request.NewOrdersRequest;
import ru.itis.lessonservlet.dto.response.OrdersResponse;
import ru.itis.lessonservlet.service.OrdersService;

import java.util.List;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;

    @GetMapping("/orders")
    public String getOrdersPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        Long userId = principal.getUser().getId();

        List<OrdersResponse> orders = ordersService.getOrdersByUserId(userId);

        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/saveOrder")
    @ResponseBody
    public ResponseEntity<?> saveOrder(
            @RequestBody NewOrdersRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        try {
            if (request.getItems() == null || request.getItems().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Невозможно оформить пустой заказ.");
            }

            Long userId = principal.getUser().getId();
            OrdersResponse response = ordersService.createOrderIfNotExists(request, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: " + e.getMessage());
        }
    }
}
