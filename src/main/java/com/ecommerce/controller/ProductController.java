package com.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entity.Product;
import com.ecommerce.repo.ProductRepository;


@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private static final String UPLOAD_DIR = "C:/ecommerce-images/";

    @Autowired
    private ProductRepository productRepo;

    // ADD PRODUCT WITH IMAGE
    @PostMapping("/add")
    public Product addProduct(
            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam double price,
            @RequestParam String category,
            @RequestParam MultipartFile image
    ) throws IOException {

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, image.getBytes());

        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCategory(category);
        product.setImage(fileName);

        return productRepo.save(product);
    }


    // GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }
}
