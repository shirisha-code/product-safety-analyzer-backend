package com.productsafety.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.productsafety.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    List<Product> findByCategoryContainingIgnoreCase(String category);

    Optional<Product> findByNameIgnoreCase(String name);
}