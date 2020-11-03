package com.barbulescu.springsecurity.service;

import com.barbulescu.springsecurity.db.Product;
import com.barbulescu.springsecurity.db.ProductRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PreFilter(value = "filterObject.owner == authentication.name")
    public List<Product> sellProducts(List<Product> products) {
        return products;
    }


    @PostFilter("filterObject.owner == authentication.name")
    public List<Product> findProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "beer", "nikolai"));
        products.add(new Product(2, "candy", "nikolai"));
        products.add(new Product(3, "chocolate", "julien"));

        return products;
    }

    public List<Product> findProducts(String text) {
        return productRepository.findProductByNameContains(text);
    }
}
