package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private SaleRepository saleRepository;

    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
    
    public double getTotalRevenue() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .mapToDouble(sale -> sale.getQuantity() * sale.getProduct().getPrice())
                .sum();
    }

   
    public double getRevenueByProduct(int productId) {
        List<Sale> sales = saleRepository.findByProductId(productId);
        return sales.stream()
                .mapToDouble(sale -> sale.getQuantity() * sale.getProduct().getPrice())
                .sum();
    }
}
