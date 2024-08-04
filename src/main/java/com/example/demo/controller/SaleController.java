package com.example.demo.controller;

import com.example.demo.entity.Sale;
import com.example.demo.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable int id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    public void addSale(@RequestBody Sale sale) {
        saleService.addSale(sale);
    }

    @PutMapping("/{id}")
    public void updateSale(@PathVariable int id, @RequestBody Sale sale) {
        saleService.updateSale(id, sale);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable int id) {
        saleService.deleteSale(id);
    }
}

