package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import ru.itis.lessonservlet.dto.response.ListCategoriesResponse;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.CategoryService;
import ru.itis.lessonservlet.service.ProductService;

import java.io.IOException;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("springContext");

        this.productService = context.getBean("productService", ProductService.class);
        this.categoryService = context.getBean("categoryService", CategoryService.class);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());
        ListCategoriesResponse listCategoryResponse = categoryService.getAllCategories();


        session.setAttribute("products", listProductsResponse);
        session.setAttribute("categories", listCategoryResponse);

        req.getRequestDispatcher("jsp/products.jsp").forward(req, resp);
    }
}
