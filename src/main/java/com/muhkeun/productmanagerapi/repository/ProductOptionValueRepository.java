package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.model.entity.ProductOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue, Long> {
}
