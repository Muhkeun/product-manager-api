package com.muhkeun.productmanagerapi.controller;

import com.muhkeun.productmanagerapi.model.dto.ProductRequest;
import com.muhkeun.productmanagerapi.model.entity.CustomUserDetails;
import com.muhkeun.productmanagerapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public ResponseEntity<Void> createProduct(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ProductRequest request) {
        productService.createProduct(userDetails.getUser(), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
