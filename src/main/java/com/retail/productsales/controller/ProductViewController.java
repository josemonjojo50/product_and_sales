package com.retail.productsales.controller;

import com.retail.productsales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/products")
public class ProductViewController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public String listProducts(Model model, @RequestParam(defaultValue = "0") int page) {
		int pageSize = 5; 
		var productPage = productService.getAllProducts(page, pageSize);

		model.addAttribute("productPage", productPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());

		return "product-list";
	}

	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
		productService.deleteProduct(id);
		redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
		return "redirect:/products";
	}

}
