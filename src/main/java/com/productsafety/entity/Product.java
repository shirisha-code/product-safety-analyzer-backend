package com.productsafety.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private Integer id;

    private String name;
    private String brand;
    private String category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductIngredient> productIngredients;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ProductIngredient> getProductIngredients() {
        return productIngredients;
    }

    public void setProductIngredients(List<ProductIngredient> productIngredients) {
        this.productIngredients = productIngredients;
    }
}