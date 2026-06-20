package com.productsafety.repository;

import com.productsafety.entity.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductIngredientRepository
        extends JpaRepository<ProductIngredient, Long> {
    List<ProductIngredient> findByProduct_Id(Integer productId);
}
