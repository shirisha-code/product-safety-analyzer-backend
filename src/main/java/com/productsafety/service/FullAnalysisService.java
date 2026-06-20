package com.productsafety.service;

import org.springframework.stereotype.Service;
import com.productsafety.dto.FullProductAnalysisResponse;
import com.productsafety.dto.ProductAnalysisResponse;

import com.productsafety.dto.RecommendedProductDTO;

import com.productsafety.entity.Product;

import com.productsafety.repository.ProductRepository;

import java.util.List;

@Service
public class FullAnalysisService {
    private final ProductDiscoveryService productDiscoveryService;

private final ProductAnalysisService productAnalysisService;

private final ProductRecommendationService productRecommendationService;

private final ProductRepository productRepository;

public FullAnalysisService(
        ProductDiscoveryService productDiscoveryService,
        ProductAnalysisService productAnalysisService,
        ProductRecommendationService productRecommendationService,
        ProductRepository productRepository) {

    this.productDiscoveryService =
            productDiscoveryService;

    this.productAnalysisService =
            productAnalysisService;

    this.productRecommendationService =
            productRecommendationService;

    this.productRepository =
            productRepository;
}
public FullProductAnalysisResponse
getFullAnalysis(String productName)
        throws Exception {

    Product product =
            productRepository
                    .findByNameIgnoreCase(productName)
                    .orElse(null);

    if (product == null) {

    ProductAnalysisResponse discoveredAnalysis =
            productDiscoveryService
                    .discoverAndAnalyze(
                            productName);

    product =
            productRepository
                    .findAll()
                    .stream()
                    .filter(p ->
                            p.getName()
                             .toLowerCase()
                             .contains(
                                     productName.toLowerCase()))
                    .findFirst()
                    .orElse(null);

    if (product == null) {
        throw new RuntimeException(
                "Unable to discover product: "
                        + productName);
    }
}

    ProductAnalysisResponse analysis =
            productAnalysisService
                    .analyzeProduct(
                            product.getId());

    List<RecommendedProductDTO>
            recommendations =
            productRecommendationService
                    .recommendProducts(
                            product.getId());

    FullProductAnalysisResponse response =
        new FullProductAnalysisResponse();

response.setProductName(
        product.getName());

response.setBrand(
        product.getBrand());

response.setCategory(
        product.getCategory());

response.setOverallScore(
        analysis.getOverallScore());

response.setSafeIngredients(
        analysis.getSafeIngredients());

response.setCautionIngredients(
        analysis.getCautionIngredients());

response.setHighRiskIngredients(
        analysis.getHighRiskIngredients());

response.setAnalysisSummary(
        analysis.getAnalysisSummary());

response.setRecommendations(
        recommendations);

    return response;
}
}