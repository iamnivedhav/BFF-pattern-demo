package com.example.webbff.controller;

import com.example.webbff.dto.web.WebOrder;
import com.example.webbff.dto.web.WebProduct;
import com.example.webbff.service.WebBffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

// To handle multiple concerns, let's remove the @RequestMapping from the class level
@RestController
@CrossOrigin(origins = "*") 
public class WebBffController {

    private final WebBffService webBffService;

    public WebBffController(WebBffService webBffService) {
        this.webBffService = webBffService;
    }

    @GetMapping("/products")
    public List<WebProduct> getAllProducts() {
        return webBffService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<WebProduct> getProductById(@PathVariable int id) {
        WebProduct product = webBffService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add the new endpoint for orders
    @GetMapping("/orders")
    public List<WebOrder> getAllOrders() {
        return webBffService.getAllOrders();
    }
}