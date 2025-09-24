package com.example.mobilebff.service;

import com.example.mobilebff.dto.mobile.MobileOrderSummary;
import com.example.mobilebff.dto.mobile.MobileProduct;
import java.util.List;

public interface MobileBffService {
    List<MobileProduct> getAllProducts();
    MobileProduct getProductById(int id);

    // Add this method for orders
    List<MobileOrderSummary> getAllOrders();
}