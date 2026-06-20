package com.productsafety.service;

import org.springframework.stereotype.Service;



@Service
public class ProductChatService {

    private final IngredientKnowledgeService ingredientKnowledgeService;

    public ProductChatService(
            IngredientKnowledgeService ingredientKnowledgeService) {

        this.ingredientKnowledgeService =
                ingredientKnowledgeService;
    }

    public String askQuestion(
        String productName,
        String summary,
        String question) throws Exception {

    String prompt =
            """
            You are a product safety expert.

            Product Name:
            %s

            Product Safety Summary:
            %s

            User Question:
            %s

            Answer in simple language.
            Keep answer under 150 words.
            """
                    .formatted(
                            productName,
                            summary,
                            question);

    String response =
            ingredientKnowledgeService
                    .askAI(prompt);

    return ingredientKnowledgeService
            .extractContent(response);
}
}