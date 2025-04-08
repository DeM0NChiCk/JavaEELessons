package ru.itis.lessonservlet.servlet;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.model.OrdersEntity;
import ru.itis.lessonservlet.service.OrdersService;
import ru.itis.lessonservlet.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

    private OrdersService ordersService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ordersService = (OrdersService) servletContext.getAttribute("ordersService");
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        List<OrdersEntity> ordersEntityList = ordersService.getOrdersByUserId(user.getId());
        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());

        session.setAttribute("orders", ordersEntityList);
        session.setAttribute("products", listProductsResponse);

        req.getRequestDispatcher("/jsp/orders.jsp").forward(req, resp);
    }
}
