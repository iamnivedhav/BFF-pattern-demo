package com.example.webbff.service;

import com.example.webbff.dto.web.WebOrder;
import com.example.webbff.dto.web.WebProduct;
import java.util.List;

public interface WebBffService {
    List<WebProduct> getAllProducts();
    WebProduct getProductById(int id);

    // Add this new method
    List<WebOrder> getAllOrders();
}