package ru.itis.lessonservlet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.CategoryService;
import ru.itis.lessonservlet.service.ProductService;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getProducts(HttpSession session) {
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");


        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());
        ListCategoriesResponse listCategoryResponse = categoryService.getAllCategories();

        session.setAttribute("products", listProductsResponse);
        session.setAttribute("categories", listCategoryResponse);

        return "products";
    }
}
