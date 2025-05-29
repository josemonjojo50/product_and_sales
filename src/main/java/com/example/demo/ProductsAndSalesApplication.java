package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.service.ProductService;
import com.example.demo.service.SaleService;

import java.time.LocalDate;

@SpringBootApplication
public class ProductsAndSalesApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    public static void main(String[] args) {
        SpringApplication.run(ProductsAndSalesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
//        Product product1 = new Product("Product 1", "Description 1", 100.0, 10);
//        Product product2 = new Product("Product 2", "Description 2", 200.0, 20);
//
//        productService.addProduct(product1);
//        productService.addProduct(product2);
//
//        Sale sale1 = new Sale(product1, 2, LocalDate.now());
//        Sale sale2 = new Sale(product2, 3, LocalDate.now());
//
//        saleService.addSale(sale1);
//        saleService.addSale(sale2);
//
//        // Demonstrate the revenue calculation methods
//        double totalRevenue = productService.getTotalRevenue();
//        double revenueByProduct1 = productService.getRevenueByProduct(product1.getId());
//
//        System.out.println("Total Revenue: " + totalRevenue);
//        System.out.println("Revenue for Product 1: " + revenueByProduct1);
    }
}

