package com.barbulescu.springsecurity.service;

import com.barbulescu.springsecurity.db.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/sell")
    List<Product> sell() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "beer", "nikolai"));
        products.add(new Product(2, "candy", "nikolai"));
        products.add(new Product(3, "chocolate", "julien"));

        return productService.sellProducts(products);
    }

    @GetMapping("/find")
    List<Product> findProducts() {
        return productService.findProducts();
    }

    @GetMapping("/products/{text}")
    public List<Product> findProductsContaining(@PathVariable String text) {
        return productService.findProducts(text);
    }
}
