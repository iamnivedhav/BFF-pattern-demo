package com.example.mobilebff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.mobilebff.client")
public class MobileBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileBffApplication.class, args);
    }
}