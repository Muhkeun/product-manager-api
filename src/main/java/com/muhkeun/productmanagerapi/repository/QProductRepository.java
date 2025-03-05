package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QProductRepository {
    Page<ProductResponse> searchProductsByUserId(Long userId, String keyword, SearchKeywordType keywordType, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
