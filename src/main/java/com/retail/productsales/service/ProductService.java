package com.retail.productsales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.retail.productsales.entity.Product;
import com.retail.productsales.entity.Sale;
import com.retail.productsales.exception.ResourceNotFoundException;
import com.retail.productsales.repository.ProductRepository;
import com.retail.productsales.repository.SaleRepository;

import java.util.List;

@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SaleRepository saleRepository;

	public Page<Product> getAllProducts(int page, int size) {
		logger.info("Fetching all products with page = {}, size = {}", page, size);
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);
	}
	
	public List<Product> getAllProductsNoPagination() {
	    return productRepository.findAll();
	}

	public Product getProductById(int id) {
		logger.info("Fetching product by ID: {}", id);
		return productRepository.findById(id).orElseThrow(() -> {
			logger.warn("Product with ID {} not found", id);
			return new ResourceNotFoundException("Product with ID " + id + " not found");
		});
	}

	public Product addProduct(Product product) {
		logger.info("Adding product: {}", product.getName());
		return productRepository.save(product);
	}

	public Product updateProduct(int id, Product product) {
		logger.info("Updating product ID: {}", id);
		Product existing = getProductById(id);
		existing.setName(product.getName());
		existing.setDescription(product.getDescription());
		existing.setPrice(product.getPrice());
		existing.setQuantity(product.getQuantity());
		Product updated = productRepository.save(existing);
		logger.info("Product ID {} updated successfully", id);
		return updated;
	}

	public void deleteProduct(int id) {
		logger.info("Deleting product with ID: {}", id);
		if (!productRepository.existsById(id)) {
			logger.warn("Cannot delete. Product with ID {} not found", id);
			throw new ResourceNotFoundException("Product with ID " + id + " not found");
		}
		productRepository.deleteById(id);
		logger.info("Product with ID {} deleted successfully", id);
	}

	public double getTotalRevenue() {
		logger.info("Calculating total revenue");
		double revenue = saleRepository.findAll().stream()
				.mapToDouble(sale -> sale.getProduct().getPrice() * sale.getQuantity()).sum();
		logger.debug("Total revenue calculated: {}", revenue);
		return revenue;
	}

	public double getRevenueByProduct(int productId) {
		logger.info("Calculating revenue for product ID: {}", productId);
		if (!productRepository.existsById(productId)) {
			logger.warn("Product with ID {} not found for revenue calculation", productId);
			throw new ResourceNotFoundException("Product with ID " + productId + " not found");
		}
		List<Sale> sales = saleRepository.findByProductId(productId);
		double revenue = sales.stream().mapToDouble(sale -> sale.getQuantity() * sale.getProduct().getPrice()).sum();
		logger.debug("Revenue for product ID {} is {}", productId, revenue);
		return revenue;
	}
}
