package com.retail.productsales.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties("sales")
	@JoinColumn(name = "productId", nullable = false)
	@NotNull(message = "Product must not be null")
	private Product product;

	@Positive(message = "Quantity must be greater than zero")
	private int quantity;

	@NotNull(message = "Sale date must not be null")
	private LocalDate saleDate;

	public Sale() {
	}

	public Sale(int id, @NotNull(message = "Product must not be null") Product product,
			@Positive(message = "Quantity must be greater than zero") int quantity,
			@NotNull(message = "Sale date must not be null") LocalDate saleDate) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.saleDate = saleDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
}
