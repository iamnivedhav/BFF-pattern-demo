package com.example.mobilebff.service;

import com.example.mobilebff.client.OrderServiceClient;
import com.example.mobilebff.client.ProductServiceClient;
import com.example.mobilebff.dto.downstream.DsOrder;
import com.example.mobilebff.dto.downstream.DsProduct;
import com.example.mobilebff.dto.mobile.MobileOrderSummary;
import com.example.mobilebff.dto.mobile.MobileProduct;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileBffServiceImpl implements MobileBffService {

    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;

    public MobileBffServiceImpl(ProductServiceClient productServiceClient, OrderServiceClient orderServiceClient) {
        this.productServiceClient = productServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    // --- Product Methods (Corrected Implementation) ---
    @Override
    public List<MobileProduct> getAllProducts() {
        List<DsProduct> downstreamProducts = productServiceClient.getAllProducts();
        if (downstreamProducts == null) {
            return Collections.emptyList();
        }
        return downstreamProducts.stream()
                .map(this::transformToMobileProduct)
                .collect(Collectors.toList());
    }

    @Override
    public MobileProduct getProductById(int id) {
        DsProduct downstreamProduct = productServiceClient.getProductById(id);
        if (downstreamProduct == null) {
            return null;
        }
        return transformToMobileProduct(downstreamProduct);
    }

    private MobileProduct transformToMobileProduct(DsProduct dsProduct) {
        MobileProduct mobileProduct = new MobileProduct();
        mobileProduct.setId(dsProduct.getId());
        mobileProduct.setName(dsProduct.getName());
        mobileProduct.setPrice(dsProduct.getPrice());
        mobileProduct.setImage(dsProduct.getImage());
        mobileProduct.setDescription(dsProduct.getDescription());
        mobileProduct.setAvgRating(dsProduct.getRating()); 
        return mobileProduct;
    }

    // --- Order Methods (Correct Implementation) ---
    @Override
    public List<MobileOrderSummary> getAllOrders() {
        List<DsOrder> downstreamOrders = orderServiceClient.getAllOrders();
        if (downstreamOrders == null) {
            return Collections.emptyList();
        }
        return downstreamOrders.stream()
                .map(this::transformToMobileOrderSummary)
                .collect(Collectors.toList());
    }

    private MobileOrderSummary transformToMobileOrderSummary(DsOrder dsOrder) {
        MobileOrderSummary summary = new MobileOrderSummary();
        summary.setId(dsOrder.getId());
        summary.setDate(dsOrder.getDate());
        summary.setStatus(dsOrder.getStatus());
        summary.setTotalAmount(dsOrder.getTotalAmount());
        return summary;
    }
}