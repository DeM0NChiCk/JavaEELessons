package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import ru.itis.lessonservlet.dto.request.OrderRequest;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.mapper.OrderMapper;
import ru.itis.lessonservlet.service.OrdersService;

import java.io.IOException;
import java.time.LocalDateTime;

import static ru.itis.lessonservlet.model.OrdersEntity.STATUS_PENDING;


@WebServlet("/saveOrder")
public class SaveOrdersServlet extends HttpServlet {

    private OrdersService ordersService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("springContext");

        this.ordersService = context.getBean("ordersService", OrdersService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));

        HttpSession session = req.getSession(true);
        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        Long userId = user.getId();

        OrderRequest orderRequest = OrderRequest.builder()
                .userId(userId)
                .productId(productId)
                .statusCode(STATUS_PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        ordersService.createOrder(orderRequest);

        resp.sendRedirect("/orders");
    }
}
