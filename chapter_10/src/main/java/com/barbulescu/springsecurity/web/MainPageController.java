package com.barbulescu.springsecurity.web;

import com.barbulescu.springsecurity.db.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    private final ProductRepository productRepository;

    public MainPageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/main")
    public String main(Authentication a, Model model) {
        model.addAttribute("username", a.getName());
        model.addAttribute("products", productRepository.findAll());
        return "main.html";
    }
}
