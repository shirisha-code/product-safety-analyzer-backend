package com.productsafety.service;

import org.springframework.stereotype.Service;
import com.productsafety.entity.IngredientKnowledge;
import com.productsafety.entity.IngredientKnowledge;
import com.productsafety.service.IngredientKnowledgeService;
@Service
public class IngredientAnalysisService {

    private final IngredientKnowledgeService ingredientKnowledgeService;

    public IngredientAnalysisService(
            IngredientKnowledgeService ingredientKnowledgeService) {

        this.ingredientKnowledgeService =
                ingredientKnowledgeService;
    }

    public IngredientKnowledge analyzeIngredient(String ingredientName)
        throws Exception {

    IngredientKnowledge ingredient =
            ingredientKnowledgeService
                    .getIngredientFromDatabase(
                            ingredientName);

    if (ingredient != null) {
    return ingredient;
}

IngredientKnowledge saved =
        ingredientKnowledgeService
                .createIngredientFromAI(
                        ingredientName);

return saved;
}
}