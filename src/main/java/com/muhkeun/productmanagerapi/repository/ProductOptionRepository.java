package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.model.entity.ProductOption;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    void deleteByProductId(Long productId);

    @EntityGraph(attributePaths = "values")
    List<ProductOption> findALLByProductUserIdAndProductId(Long userId, Long productId);
}
