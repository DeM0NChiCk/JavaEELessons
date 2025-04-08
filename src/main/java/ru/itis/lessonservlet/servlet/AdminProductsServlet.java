package ru.itis.lessonservlet.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletFileUpload;
import org.apache.commons.io.IOUtils;
import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;
import ru.itis.lessonservlet.dto.response.UserDataResponse;
import ru.itis.lessonservlet.service.ProductService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductsServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        productService = (ProductService) context.getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        UserDataResponse user = (UserDataResponse) session.getAttribute("user");

        ListProductsResponse listProductsResponse = productService.getAllProducts(user.getId());

        session.setAttribute("products", listProductsResponse);

        req.getRequestDispatcher("../jsp/adminProducts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!JakartaServletFileUpload.isMultipartContent(req)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Form must contain enctype=multipart/form-data");
            return;
        }

        DiskFileItemFactory factory = DiskFileItemFactory.builder()
                .setCharset(StandardCharsets.UTF_8)
                .get();

        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);

        try {
            String name = "";
            String description = "";
            double price = 0d;
            List<CategoryRequest> categories = new ArrayList<>();
            int quantity = 0;
            byte[] image = {};

            List<DiskFileItem> items = upload.parseRequest(req);

            for (DiskFileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "name" -> name = item.getString();
                        case "description" -> description = item.getString();
                        case "price" -> price = Double.parseDouble(item.getString());
                        case "quantity" -> quantity = Integer.parseInt(item.getString());
                        case "categories" -> categories.add(new CategoryRequest(item.getString()));
                    }
                } else {
                    if ("image".equals(item.getFieldName())) {
                        try (InputStream inputStream = item.getInputStream()) {
                            if (inputStream != null) {
                                image = IOUtils.toByteArray(inputStream);
                            } else {
                                image = new byte[]{1};
                            }

                        }
                    }
                }
            }

            NewProductRequest newProductRequest = NewProductRequest.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .image(image)
                    .build();

            productService.saveNewProduct(newProductRequest, categories);

            resp.sendRedirect("/admin/products");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error processing upload: " + e.getMessage());
        }
    }
}
