package ru.itis.lessonservlet.service;

import ru.itis.lessonservlet.dto.request.CategoryRequest;
import ru.itis.lessonservlet.dto.request.NewProductRequest;
import ru.itis.lessonservlet.dto.response.ListProductsResponse;

import java.util.List;

public interface ProductService {

    ListProductsResponse getAllProducts();

    void saveNewProduct(NewProductRequest request, List<CategoryRequest> requestList);
}
