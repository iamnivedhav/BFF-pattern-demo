package com.example.webbff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// Add the basePackages attribute here
@EnableFeignClients(basePackages = "com.example.webbff.client")
public class WebBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBffApplication.class, args);
    }
}