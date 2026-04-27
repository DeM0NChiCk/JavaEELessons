package ru.itis.semestr.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semestr.config.security.UserPrincipal;
import ru.itis.semestr.dto.response.ListCategoriesResponse;
import ru.itis.semestr.dto.response.ListProductsResponse;
import ru.itis.semestr.entity.UserEntity;
import ru.itis.semestr.service.CategoryService;
import ru.itis.semestr.service.ProductService;

@Hidden
@Controller
@RequestMapping("/web/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getProducts(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        UserEntity user = principal.getUser();

        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());
        ListCategoriesResponse listCategoryResponse = categoryService.getAllCategories();

        model.addAttribute("products", listProductsResponse.getProducts());
        model.addAttribute("categories", listCategoryResponse.getCategories());

        return "products";
    }
}
