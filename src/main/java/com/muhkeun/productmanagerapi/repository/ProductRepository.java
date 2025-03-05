package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.model.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
