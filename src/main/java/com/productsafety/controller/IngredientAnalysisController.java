package com.productsafety.controller;

import com.productsafety.entity.IngredientKnowledge;
import com.productsafety.service.IngredientAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient-analysis")
public class IngredientAnalysisController {

    private final IngredientAnalysisService ingredientAnalysisService;

    public IngredientAnalysisController(
            IngredientAnalysisService ingredientAnalysisService) {

        this.ingredientAnalysisService = ingredientAnalysisService;
    }

    @GetMapping("/{name}")
    public IngredientKnowledge analyzeIngredient(
            @PathVariable String name) 
            throws Exception {

        return ingredientAnalysisService.analyzeIngredient(name);
    }
}