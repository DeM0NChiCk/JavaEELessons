package ru.itis.lessonservlet.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @GetMapping
    public String getAdminProductsPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());
        model.addAttribute("products", listProductsResponse);

        return "adminProducts";
    }

    @PostMapping
    public String createProduct(
            @ModelAttribute NewProductRequest newProductRequest,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categories") List<String> categoryIds,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            byte[] image = imageFile.isEmpty() ? new byte[]{1} : IOUtils.toByteArray(imageFile.getInputStream());

            List<CategoryRequest> categories = new ArrayList<>();
            for (String categoryId : categoryIds) {
                categories.add(new CategoryRequest(categoryId));
            }

            newProductRequest = NewProductRequest.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .image(image)
                    .build();

            productService.saveNewProduct(newProductRequest, categories);

            return "redirect:/admin/products";

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}
