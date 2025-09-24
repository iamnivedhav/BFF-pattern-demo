package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private List<Product> products;

    public ProductServiceImpl() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/data/products.json");
            products = mapper.readValue(is, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}
