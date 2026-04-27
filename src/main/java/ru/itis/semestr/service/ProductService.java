package ru.itis.semestr.service;

import ru.itis.semestr.dto.request.CategoryRequest;
import ru.itis.semestr.dto.request.NewProductRequest;
import ru.itis.semestr.dto.response.ListProductsResponse;

import java.util.List;

public interface ProductService {

    ListProductsResponse getAllProducts(Long userId);

    void saveNewProduct(NewProductRequest request, List<CategoryRequest> requestList);

    void updateProduct(Long id, String name, String description, double price, int quantity, byte[] image);

    void deleteProduct(Long id);

    ListProductsResponse getOrderedProductsByUserId(Long userId);

}
