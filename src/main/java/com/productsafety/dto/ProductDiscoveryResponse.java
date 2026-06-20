package com.productsafety.dto;

import java.util.List;

public class ProductDiscoveryResponse {

    private String productName;

    private List<String> ingredients;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}