package com.productsafety.service;

import org.springframework.stereotype.Service;

import com.productsafety.dto.ProductAnalysisResponse;
import com.productsafety.dto.ProductDetailResponse;
import com.productsafety.dto.IngredientDetailResponse;
import com.productsafety.entity.IngredientKnowledge;
import com.productsafety.entity.Product;
import com.productsafety.entity.ProductIngredient;
import com.productsafety.repository.IngredientKnowledgeRepository;
import com.productsafety.repository.ProductIngredientRepository;
import com.productsafety.repository.ProductRepository;
import com.productsafety.service.IngredientKnowledgeService;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAnalysisService {
    private final ProductRepository productRepository;
private final ProductIngredientRepository productIngredientRepository;
private final IngredientKnowledgeRepository ingredientKnowledgeRepository;
private final IngredientKnowledgeService ingredientKnowledgeService;

public ProductAnalysisService(
        ProductRepository productRepository,
        ProductIngredientRepository productIngredientRepository,
        IngredientKnowledgeRepository ingredientKnowledgeRepository,
        IngredientKnowledgeService ingredientKnowledgeService) {

    this.productRepository = productRepository;
    this.productIngredientRepository = productIngredientRepository;
    this.ingredientKnowledgeRepository = ingredientKnowledgeRepository;
    this.ingredientKnowledgeService = ingredientKnowledgeService;
}
public ProductAnalysisResponse analyzeProduct(Integer productId) {

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    ProductAnalysisResponse response = new ProductAnalysisResponse();

    response.setProductId(product.getId().longValue());
    response.setProductName(product.getName());

    List<ProductIngredient> productIngredients =
            productIngredientRepository.findByProduct_Id(productId);

    int totalRiskScore = 0;

    List<String> safeIngredients = new ArrayList<>();
    List<String> cautionIngredients = new ArrayList<>();
    List<String> highRiskIngredients = new ArrayList<>();

    for (ProductIngredient pi : productIngredients) {

        String ingredientName =
                pi.getIngredient().getIngredientName();

        IngredientKnowledge knowledge =
                ingredientKnowledgeRepository
                        .findByIngredientName(ingredientName)
                        .orElse(null);

        if (knowledge == null) {

            try {

                knowledge =
                        ingredientKnowledgeService
                                .createIngredientFromAI(
                                        ingredientName);

            } catch (Exception e) {

                continue;
            }
        }

        Integer riskScore = knowledge.getRiskScore();

        if (riskScore == null) {
            riskScore = 50;
        }

        totalRiskScore += riskScore;

        if (riskScore >= 70) {

            safeIngredients.add(ingredientName);

        } else if (riskScore >= 31) {

            cautionIngredients.add(ingredientName);

        } else {

            highRiskIngredients.add(ingredientName);
        }
    }

    int ingredientCount = productIngredients.size();

    int overallScore = 100;

    if (ingredientCount > 0) {
        overallScore = totalRiskScore / ingredientCount;
    }

    response.setOverallScore(overallScore);

    response.setSafeIngredients(safeIngredients);

    response.setCautionIngredients(cautionIngredients);

    response.setHighRiskIngredients(highRiskIngredients);

    String summary;

    if (!highRiskIngredients.isEmpty()) {

        summary =
                "This product contains high-risk ingredients that may require caution.";

    } else if (!cautionIngredients.isEmpty()) {

        summary =
                "This product is generally safe but contains some ingredients that may cause irritation for sensitive users.";

    } else {

        summary =
                "This product appears safe based on the analyzed ingredients.";
    }

    response.setAnalysisSummary(summary);

    return response;
}


public ProductDetailResponse getProductDetails(Integer productId) {

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    ProductDetailResponse response =
            new ProductDetailResponse();

    response.setProductId(product.getId());
    response.setProductName(product.getName());

    List<ProductIngredient> productIngredients =
            productIngredientRepository.findByProduct_Id(productId);

    List<IngredientDetailResponse> ingredientDetails =
            new ArrayList<>();

    for (ProductIngredient pi : productIngredients) {

        String ingredientName =
                pi.getIngredient().getIngredientName();

        IngredientKnowledge knowledge =
                ingredientKnowledgeRepository
                        .findByIngredientName(ingredientName)
                        .orElse(null);
                        if (knowledge == null) {

    try {

        System.out.println(
                "Generating knowledge for: "
                        + ingredientName);

        knowledge =
                ingredientKnowledgeService
                        .createIngredientFromAI(
                                ingredientName);

    } catch (Exception e) {

        System.out.println(
                "Failed to analyze: "
                        + ingredientName);

        continue;
    }
}

        IngredientDetailResponse detail =
                new IngredientDetailResponse();

        detail.setIngredientName(ingredientName);

        if (knowledge != null) {

    detail.setRiskScore(
            knowledge.getRiskScore());

    detail.setGeneralSafety(
            knowledge.getGeneralSafety());

    detail.setWarningMessage(
            knowledge.getWarningMessage());

    detail.setEffectOnBody(
            knowledge.getEffectOnBody());
    detail.setAlternativeIngredients(
        knowledge.getAlternativeIngredients());
}

        ingredientDetails.add(detail);
    }

    response.setIngredients(ingredientDetails);

    return response;
}
}
