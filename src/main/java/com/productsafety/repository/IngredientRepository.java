package com.productsafety.repository;
import java.util.Optional;
import com.productsafety.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository
        extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByIngredientNameIgnoreCase(
            String ingredientName);
}