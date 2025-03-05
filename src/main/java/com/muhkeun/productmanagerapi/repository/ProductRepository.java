package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.model.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QProductRepository {
    Optional<Product> findByUserIdAndName(Long userId, String name);
}
