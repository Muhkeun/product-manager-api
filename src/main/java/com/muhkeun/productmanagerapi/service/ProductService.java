package com.muhkeun.productmanagerapi.service;

import com.muhkeun.productmanagerapi.model.dto.ProductRequest;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(User user, ProductRequest request) {

        if (isDuplicatedProductName(request.getName())) {
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

    private boolean isDuplicatedProductName(String name) {
        return productRepository.findByName(name).isPresent();
    }
}
