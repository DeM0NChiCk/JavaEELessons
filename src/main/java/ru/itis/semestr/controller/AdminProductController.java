package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.dto.request.CategoryRequest;
import ru.itis.semestr.dto.request.NewProductRequest;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.service.ProductService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String getAdminProductsPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        UserEntity user = principal.getUser();

        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());
        model.addAttribute("products", listProductsResponse);

        return "adminProducts";
    }

    @PostMapping("/products")
    public String createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categories") List<String> categoryIds,
            @RequestParam("image") MultipartFile imageFile
    ) {


        try {

            String contentType = imageFile.getContentType();

            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                    contentType.equals("image/png") ||
                    contentType.equals("image/webp") ||
                    contentType.equals("image/gif") ||
                    contentType.equals("image/jpg"))) {

                String errorMessage = URLEncoder.encode("Файл должен быть изображением (jpg, png, webp, gif)", StandardCharsets.UTF_8);

                return "redirect:/error-auth?err=" + errorMessage;
            }

            byte[] image = imageFile.isEmpty() ? new byte[]{1} : IOUtils.toByteArray(imageFile.getInputStream());

            List<CategoryRequest> categories = new ArrayList<>();
            for (String categoryId : categoryIds) {
                categories.add(new CategoryRequest(categoryId));
            }

            NewProductRequest newProductRequest = NewProductRequest.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .image(image)
                    .build();

            productService.saveNewProduct(newProductRequest, categories);

            return "redirect:products";

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        byte[] image = null;
        try {

            String contentType = imageFile.getContentType();

            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                            contentType.equals("image/png") ||
                            contentType.equals("image/webp") ||
                            contentType.equals("image/gif") ||
                            contentType.equals("image/jpg"))) {

                String errorMessage = URLEncoder.encode("Файл должен быть изображением (jpg,png,webp,gif)", StandardCharsets.UTF_8);

                return ResponseEntity.badRequest().body(errorMessage);
            }

            if (!imageFile.isEmpty()) {
                image = IOUtils.toByteArray(imageFile.getInputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки изображения: " + e.getMessage());
        }

        productService.updateProduct(id, name, description, price, quantity, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при удалении продукта: " + e.getMessage());
        }

    }
}