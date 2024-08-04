package com.example.demo.service;


import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(int id) {
        return saleRepository.findById(id).orElse(null);
    }

    public void addSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void updateSale(int id, Sale updatedSale) {
        Sale existingSale = getSaleById(id);
        if (existingSale != null) {
            updatedSale.setId(id);
            saleRepository.save(updatedSale);
        }
    }

    public void deleteSale(int id) {
        saleRepository.deleteById(id);
    }
    
    
}

