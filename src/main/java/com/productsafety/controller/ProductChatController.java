package com.productsafety.controller;

import org.springframework.web.bind.annotation.*;

import com.productsafety.dto.ProductQuestionRequest;
import com.productsafety.service.ProductChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ProductChatController {

    private final ProductChatService productChatService;

    public ProductChatController(
            ProductChatService productChatService) {

        this.productChatService = productChatService;
    }

    @GetMapping("/test")
public String test() throws Exception {

    return productChatService.askQuestion(
            "Dove Soap",
            "Generally safe product",
            "Can sensitive skin people use this?"
    );
}

    @PostMapping
    public String askQuestion(
            @RequestBody ProductQuestionRequest request)
            throws Exception {

        return productChatService.askQuestion(
                request.getProductName(),
                request.getSummary(),
                request.getQuestion());
    }
}