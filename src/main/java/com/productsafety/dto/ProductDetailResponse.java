package com.productsafety.dto;

import java.util.List;

public class ProductDetailResponse {

    private Integer productId;
    private String productName;

    private List<IngredientDetailResponse> ingredients;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<IngredientDetailResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(
            List<IngredientDetailResponse> ingredients) {
        this.ingredients = ingredients;
    }
}
