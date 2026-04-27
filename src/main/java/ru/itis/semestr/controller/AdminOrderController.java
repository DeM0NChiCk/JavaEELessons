package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.semestr.dto.response.OrdersAllResponse;
import ru.itis.semestr.service.OrdersService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminOrderController {

    private final OrdersService ordersService;

    @GetMapping("/orders")
    public String getAllOrdersPage(Model model) {
        List<OrdersAllResponse> orders = ordersService.getAllOrders();
        model.addAttribute("orders", orders);
        return "adminOrder";
    }

    @PostMapping("/orders/updateStatus")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, String> payload) {
        try {
            UUID orderNumber = UUID.fromString(payload.get("orderNumber"));
            String statusCode = payload.get("statusCode");

            ordersService.updateOrderStatus(orderNumber, statusCode);
            return ResponseEntity.ok("Статус обновлён.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка обновления: " + e.getMessage());
        }
    }
}
