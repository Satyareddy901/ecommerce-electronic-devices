package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Product;
import com.ecommerce.repo.ProductRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private ProductRepository productRepo;

    // Add product
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productRepo.save(product);
    }

    // Get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // Delete product
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "Product deleted successfully";
    }
}
