package com.example.mobilebff.controller;

import com.example.mobilebff.dto.mobile.MobileOrderSummary;
import com.example.mobilebff.dto.mobile.MobileProduct;
import com.example.mobilebff.service.MobileBffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MobileBffController {

    private final MobileBffService mobileBffService;

    public MobileBffController(MobileBffService mobileBffService) {
        this.mobileBffService = mobileBffService;
    }

    @GetMapping("/products")
    public List<MobileProduct> getAllProducts() {
        return mobileBffService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<MobileProduct> getProductById(@PathVariable int id) {
        // This is the missing implementation
        MobileProduct product = mobileBffService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders")
    public List<MobileOrderSummary> getAllOrders() {
        return mobileBffService.getAllOrders();
    }
}