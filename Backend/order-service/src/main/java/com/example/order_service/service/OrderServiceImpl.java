package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private List<Order> orders;

    @PostConstruct
    public void loadOrdersFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Order>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/orders.json");
            orders = mapper.readValue(inputStream, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            orders = Collections.emptyList(); // Prevent NullPointerException on failure
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }
}