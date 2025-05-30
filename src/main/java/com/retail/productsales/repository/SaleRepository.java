package com.retail.productsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.productsales.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
	List<Sale> findByProductId(int productId);
}
