package com.example.productservice.service;

import com.example.productservice.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
}
