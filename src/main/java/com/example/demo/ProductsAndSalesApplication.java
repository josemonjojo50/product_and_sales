package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
//import com.example.demo.entity.Sale;
import com.example.demo.service.ProductService;

import java.time.LocalDate;

@SpringBootApplication
public class ProductsAndSalesApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(ProductsAndSalesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Product product1 = new Product(1,"Product 1", "Description 1", 100.0, 10);
        Product product2 = new Product(2,"Product 2", "Description 2", 200.0, 20);

        productService.addProduct(product1);
        productService.addProduct(product2);

        Sale sale1 = new Sale(product1, 2, LocalDate.now());
        Sale sale2 = new Sale(product2, 3, LocalDate.now());

        product1.getSales().add(sale1);
        product2.getSales().add(sale2);

        productService.updateProduct(product1.getId(), product1);
        productService.updateProduct(product2.getId(), product2);
    }
}

