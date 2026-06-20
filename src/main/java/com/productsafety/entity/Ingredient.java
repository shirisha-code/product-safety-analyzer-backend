package com.productsafety.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingredients")

public class Ingredient {

    @Id
    @jakarta.persistence.Column(name = "ingredient_id")
    private Integer ingredientId;

    @jakarta.persistence.Column(name = "ingredient_name")
    private String ingredientName;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient")
    private List<ProductIngredient> productIngredients;
    

    public Ingredient() {
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public List<ProductIngredient> getProductIngredients() {
        return productIngredients;
    }

    public void setProductIngredients(List<ProductIngredient> productIngredients) {
        this.productIngredients = productIngredients;
    }
}
