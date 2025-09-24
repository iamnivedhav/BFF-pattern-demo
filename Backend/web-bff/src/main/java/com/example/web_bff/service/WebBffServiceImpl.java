package com.example.webbff.service;

import com.example.webbff.client.OrderServiceClient;
import com.example.webbff.client.ProductServiceClient;
import com.example.webbff.dto.downstream.DsOrder;
import com.example.webbff.dto.downstream.DsProduct;
import com.example.webbff.dto.web.WebOrder;
import com.example.webbff.dto.web.WebProduct;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebBffServiceImpl implements WebBffService {

    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient; // Inject the new client

    // Update the constructor
    public WebBffServiceImpl(ProductServiceClient productServiceClient, OrderServiceClient orderServiceClient) {
        this.productServiceClient = productServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    // ... (existing product methods remain the same) ...

    @Override
    public List<WebProduct> getAllProducts() {
        List<DsProduct> downstreamProducts = productServiceClient.getAllProducts();
        if (downstreamProducts == null) {
            return Collections.emptyList();
        }
        return downstreamProducts.stream()
                .map(this::transformToWebProduct)
                .collect(Collectors.toList());
    }

    @Override
    public WebProduct getProductById(int id) {
        DsProduct downstreamProduct = productServiceClient.getProductById(id);
        if (downstreamProduct == null) {
            return null;
        }
        return transformToWebProduct(downstreamProduct);
    }
    
    // Implement the new method for orders
    @Override
    public List<WebOrder> getAllOrders() {
        List<DsOrder> downstreamOrders = orderServiceClient.getAllOrders();
        if (downstreamOrders == null) {
            return Collections.emptyList();
        }
        // Since WebOrder and DsOrder are identical for now, we can map them directly
        return downstreamOrders.stream()
                .map(dsOrder -> {
                    WebOrder webOrder = new WebOrder();
                    BeanUtils.copyProperties(dsOrder, webOrder);
                    return webOrder;
                })
                .collect(Collectors.toList());
    }

    private WebProduct transformToWebProduct(DsProduct dsProduct) {
        WebProduct webProduct = new WebProduct();
        webProduct.setId(dsProduct.getId());
        webProduct.setName(dsProduct.getName());
        webProduct.setBrand(dsProduct.getBrand());
        webProduct.setPrice(dsProduct.getPrice());
        webProduct.setShortDescription(dsProduct.getShortDesc());
        webProduct.setImage(dsProduct.getImage());
        webProduct.setAvgRating(dsProduct.getRating());
        webProduct.setStock(dsProduct.getStock());
        webProduct.setFullDescription(dsProduct.getDescription());
        webProduct.setReviews(dsProduct.getReviews());
        webProduct.setTotalReviews(dsProduct.getReviews() != null ? dsProduct.getReviews().size() : 0);
        return webProduct;
    }
}