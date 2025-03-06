package com.muhkeun.productmanagerapi.service;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.ProductRequest;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.ProductRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(User user, ProductRequest request) {

        if (isDuplicatedProductName(request.getName(), user.getId())) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .shippingFee(request.getShippingFee())
                .user(user)
                .build();

        productRepository.save(product);
    }

    private boolean isDuplicatedProductName(String name, Long userId) {
        return productRepository.findByUserIdAndName(userId, name).isPresent();
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(Long userId, String keyword, SearchKeywordType keywordType, BigDecimal minPrice, BigDecimal maxPrice,
                                                Pageable pageable) {

        return productRepository.searchProductsByUserId(userId, keyword, keywordType, minPrice, maxPrice, pageable);
    }
}
