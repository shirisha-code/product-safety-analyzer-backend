package com.productsafety.dto;

import java.util.List;

public class FullProductAnalysisResponse {

    private String productName;

    private String brand;

    private String category;

    private int overallScore;

    private List<String> safeIngredients;

    private List<String> cautionIngredients;

    private List<String> highRiskIngredients;

    private String analysisSummary;

    private List<RecommendedProductDTO> recommendations;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public List<String> getSafeIngredients() {
        return safeIngredients;
    }

    public void setSafeIngredients(
            List<String> safeIngredients) {
        this.safeIngredients = safeIngredients;
    }

    public List<String> getCautionIngredients() {
        return cautionIngredients;
    }

    public void setCautionIngredients(
            List<String> cautionIngredients) {
        this.cautionIngredients = cautionIngredients;
    }

    public List<String> getHighRiskIngredients() {
        return highRiskIngredients;
    }

    public void setHighRiskIngredients(
            List<String> highRiskIngredients) {
        this.highRiskIngredients = highRiskIngredients;
    }

    public String getAnalysisSummary() {
        return analysisSummary;
    }

    public void setAnalysisSummary(
            String analysisSummary) {
        this.analysisSummary = analysisSummary;
    }

    public List<RecommendedProductDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(
            List<RecommendedProductDTO> recommendations) {
        this.recommendations = recommendations;
    }
}