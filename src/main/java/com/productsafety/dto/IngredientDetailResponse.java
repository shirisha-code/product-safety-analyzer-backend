package com.productsafety.dto;

public class IngredientDetailResponse {

    private String ingredientName;
    private Integer riskScore;
    private String generalSafety;

    private String warningMessage;
    private String effectOnBody;
    private String alternativeIngredients;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getGeneralSafety() {
        return generalSafety;
    }

    public void setGeneralSafety(String generalSafety) {
        this.generalSafety = generalSafety;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getEffectOnBody() {
        return effectOnBody;
    }

    public void setEffectOnBody(String effectOnBody) {
        this.effectOnBody = effectOnBody;
    }

    public String getAlternativeIngredients() {
        return alternativeIngredients;
    }
    public void setAlternativeIngredients(String alternativeIngredients) {
        this.alternativeIngredients =alternativeIngredients;
    }
}