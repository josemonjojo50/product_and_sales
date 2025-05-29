package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

import com.example.demo.service.PdfExportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private PdfExportService pdfExportService;

	@GetMapping("/all")
	public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		logger.info("Fetching all products - page: {}, size: {}", page, size);
		Page<Product> products = productService.getAllProducts(page, size);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id) {
		logger.info("Fetching product by ID: {}", id);
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
		logger.info("Adding new product: {}", product.getName());
		Product saved = productService.addProduct(product);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
		logger.info("Updating product ID {} with data: {}", id, product);
		Product updated = productService.updateProduct(id, product);
		return ResponseEntity.ok(updated);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
		logger.info("Deleting product by ID: {}", id);
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/revenue")
	public ResponseEntity<Double> getTotalRevenue() {
		logger.info("Calculating total revenue");
		double revenue = productService.getTotalRevenue();
		return ResponseEntity.ok(revenue);
	}

	@GetMapping("/revenue/{id}")
	public ResponseEntity<Double> getRevenueByProduct(@PathVariable int id) {
		logger.info("Calculating revenue for product ID: {}", id);
		double revenue = productService.getRevenueByProduct(id);
		return ResponseEntity.ok(revenue);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/download/pdf")
	public ResponseEntity<byte[]> downloadPdf() {
		logger.info("Exporting product list as PDF");
		ByteArrayInputStream bis = pdfExportService.export();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=products.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bis.readAllBytes());
	}
}
