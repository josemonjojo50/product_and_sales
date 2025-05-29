package com.example.demo.service;

import com.example.demo.entity.Sale;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SaleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

	private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

	@Autowired
	private SaleRepository saleRepository;

	public List<Sale> getAllSales() {
		logger.info("Fetching all sales");
		return saleRepository.findAll();
	}

	public Sale getSaleById(int id) {
		logger.info("Fetching sale with id: {}", id);
		return saleRepository.findById(id).orElseThrow(() -> {
			logger.error("Sale not found with id: {}", id);
			return new ResourceNotFoundException("Sale not found with id: " + id);
		});
	}

	public Sale addSale(Sale sale) {
		logger.info("Adding new sale: {}", sale);
		return saleRepository.save(sale);
	}

	public Sale updateSale(int id, Sale updatedSale) {
		logger.info("Updating sale with id: {}", id);
		Sale existingSale = getSaleById(id); // will throw if not found
		updatedSale.setId(id);
		Sale savedSale = saleRepository.save(updatedSale);
		logger.info("Updated sale: {}", savedSale);
		return savedSale;
	}

	public void deleteSale(int id) {
		logger.info("Deleting sale with id: {}", id);
		if (!saleRepository.existsById(id)) {
			logger.error("Cannot delete. Sale not found with id: {}", id);
			throw new ResourceNotFoundException("Sale not found with id: " + id);
		}
		saleRepository.deleteById(id);
		logger.info("Deleted sale with id: {}", id);
	}
}
