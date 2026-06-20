package com.productsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "ingredient_knowledge")
public class IngredientKnowledge {

    public IngredientKnowledge() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("ingredient_name")
@Column(name = "ingredient_name", unique = true, nullable = false)
private String ingredientName;

    @JsonProperty("general_safety")
@Column(name = "general_safety", columnDefinition = "TEXT")
private String generalSafety;

@JsonProperty("adult_recommendation")
@Column(name = "adult_recommendation", columnDefinition = "TEXT")
private String adultRecommendation;

@JsonProperty("children_recommendation")
@Column(name = "children_recommendation", columnDefinition = "TEXT")
private String childrenRecommendation;

@JsonProperty("pregnancy_recommendation")
@Column(name = "pregnancy_recommendation", columnDefinition = "TEXT")
private String pregnancyRecommendation;

@JsonProperty("sensitive_skin_recommendation")
@Column(name = "sensitive_skin_recommendation", columnDefinition = "TEXT")
private String sensitiveSkinRecommendation;

@JsonProperty("allergy_recommendation")
@Column(name = "allergy_recommendation", columnDefinition = "TEXT")
private String allergyRecommendation;

    @JsonProperty("risk_score")
@Column(name = "risk_score")
private Integer riskScore;

@JsonProperty("effect_on_body")
@Column(name = "effect_on_body", columnDefinition = "TEXT")
private String effectOnBody;

@JsonProperty("warning_message")
@Column(name = "warning_message", columnDefinition = "TEXT")
private String warningMessage;

@JsonProperty("restricted_countries")
@Column(name = "restricted_countries", columnDefinition = "TEXT")
private String restrictedCountries;

@JsonProperty("alternative_ingredients")
@Column(name = "alternative_ingredients", columnDefinition = "TEXT")
private String alternativeIngredients;

@JsonProperty("scientific_summary")
@Column(name = "scientific_summary", columnDefinition = "TEXT")
private String scientificSummary;

    @Column(name = "source_urls", columnDefinition = "TEXT")
    private String sourceUrls;

    @Column(name = "confidence_score")
    private Integer confidenceScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getGeneralSafety() {
        return generalSafety;
    }

    public void setGeneralSafety(String generalSafety) {
        this.generalSafety = generalSafety;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getAdultRecommendation() {
        return adultRecommendation;
    }

    public void setAdultRecommendation(String adultRecommendation) {
        this.adultRecommendation = adultRecommendation;
    }

    public String getChildrenRecommendation() {
        return childrenRecommendation;
    }

    public void setChildrenRecommendation(String childrenRecommendation) {
        this.childrenRecommendation = childrenRecommendation;
    }

    public String getPregnancyRecommendation() {
        return pregnancyRecommendation;
    }

    public void setPregnancyRecommendation(String pregnancyRecommendation) {
        this.pregnancyRecommendation = pregnancyRecommendation;
    }

    public String getSensitiveSkinRecommendation() {
        return sensitiveSkinRecommendation;
    }

    public void setSensitiveSkinRecommendation(String sensitiveSkinRecommendation) {
        this.sensitiveSkinRecommendation = sensitiveSkinRecommendation;
    }

    public String getAllergyRecommendation() {
        return allergyRecommendation;
    }

    public void setAllergyRecommendation(String allergyRecommendation) {
        this.allergyRecommendation = allergyRecommendation;
    }

    public String getEffectOnBody() {
        return effectOnBody;
    }

    public void setEffectOnBody(String effectOnBody) {
        this.effectOnBody = effectOnBody;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getRestrictedCountries() {
        return restrictedCountries;
    }

    public void setRestrictedCountries(String restrictedCountries) {
        this.restrictedCountries = restrictedCountries;
    }

    public String getAlternativeIngredients() {
        return alternativeIngredients;
    }

    public void setAlternativeIngredients(String alternativeIngredients) {
        this.alternativeIngredients = alternativeIngredients;
    }

    public String getScientificSummary() {
        return scientificSummary;
    }

    public void setScientificSummary(String scientificSummary) {
        this.scientificSummary = scientificSummary;
    }

    public String getSourceUrls() {
        return sourceUrls;
    }

    public void setSourceUrls(String sourceUrls) {
        this.sourceUrls = sourceUrls;
    }

    public Integer getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Integer confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
public String toString() {
    return "IngredientKnowledge{" +
            "ingredientName='" + ingredientName + '\'' +
            ", generalSafety='" + generalSafety + '\'' +
            ", riskScore=" + riskScore +
            '}';
}
}
