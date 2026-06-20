package com.productsafety.repository;

import com.productsafety.entity.IngredientKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface IngredientKnowledgeRepository
        extends JpaRepository<IngredientKnowledge, Integer> {

    Optional<IngredientKnowledge> findByIngredientName(String ingredientName);
}