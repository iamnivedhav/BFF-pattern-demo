package com.example.mobilebff.client;

import com.example.mobilebff.dto.downstream.DsProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {

    @GetMapping("/products")
    List<DsProduct> getAllProducts();

    @GetMapping("/products/{id}")
    DsProduct getProductById(@PathVariable("id") int id);
}