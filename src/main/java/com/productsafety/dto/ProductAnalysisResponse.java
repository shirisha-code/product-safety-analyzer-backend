package com.productsafety.dto;

import java.util.List;

public class ProductAnalysisResponse {

    private Long productId;
    private String productName;

    private int overallScore;

    private List<String> safeIngredients;

    private List<String> cautionIngredients;

    private List<String> highRiskIngredients;

    private String analysisSummary;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
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

    public void setSafeIngredients(List<String> safeIngredients) {
        this.safeIngredients = safeIngredients;
    }

    public List<String> getCautionIngredients() {
        return cautionIngredients;
    }

    public void setCautionIngredients(List<String> cautionIngredients) {
        this.cautionIngredients = cautionIngredients;
    }

    public List<String> getHighRiskIngredients() {
        return highRiskIngredients;
    }

    public void setHighRiskIngredients(List<String> highRiskIngredients) {
        this.highRiskIngredients = highRiskIngredients;
    }

    public String getAnalysisSummary() {
        return analysisSummary;
    }

    public void setAnalysisSummary(String analysisSummary) {
        this.analysisSummary = analysisSummary;
    }
}