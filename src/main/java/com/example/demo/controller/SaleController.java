package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Sale;
import com.example.demo.service.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

	private static final Logger logger = LogManager.getLogger(SaleController.class);

	@Autowired
	private SaleService saleService;

	@GetMapping
	public ResponseEntity<List<Sale>> getAllSales() {
		logger.info("Fetching all sales");
		List<Sale> sales = saleService.getAllSales();
		return ResponseEntity.ok(sales);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Sale> getSaleById(@PathVariable int id) {
		logger.info("Fetching sale with id: {}", id);
		Sale sale = saleService.getSaleById(id); // throws if not found
		return ResponseEntity.ok(sale);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Sale> addSale(@Valid @RequestBody Sale sale) {
		logger.info("Adding sale: {}", sale);
		Sale createdSale = saleService.addSale(sale);
		return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Sale> updateSale(@PathVariable int id, @RequestBody Sale sale) {
		logger.info("Updating sale with id: {}", id);
		Sale updatedSale = saleService.updateSale(id, sale); // throws if not found
		return ResponseEntity.ok(updatedSale);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteSale(@PathVariable int id) {
		logger.info("Deleting sale with id: {}", id);
		saleService.deleteSale(id); // throws if not found
		return ResponseEntity.noContent().build();
	}
}
