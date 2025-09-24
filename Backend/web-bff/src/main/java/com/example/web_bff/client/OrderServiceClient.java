package com.example.webbff.client;

import com.example.webbff.dto.downstream.DsOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderServiceClient {

    @GetMapping("/orders")
    List<DsOrder> getAllOrders();
}