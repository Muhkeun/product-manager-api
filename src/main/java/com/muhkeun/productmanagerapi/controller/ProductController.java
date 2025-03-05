package com.muhkeun.productmanagerapi.controller;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.PagedResponse;
import com.muhkeun.productmanagerapi.model.dto.ProductRequest;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import com.muhkeun.productmanagerapi.model.entity.CustomUserDetails;
import com.muhkeun.productmanagerapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 검색 기준에 맞춰 조회합니다.")
    public ResponseEntity<PagedResponse<ProductResponse>> searchProducts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "검색 키워드", example = "국물")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "검색 키워드 타입", example = "NAME")
            @RequestParam(required = false) SearchKeywordType keywordType,
            @Parameter(description = "최소 가격", example = "40")
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "최대 가격", example = "100")
            @RequestParam(required = false) BigDecimal maxPrice,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt,desc") Pageable pageable) {

        Long userId = userDetails.getUser().getId();
        Page<ProductResponse> productPage = productService.searchProducts(userId, keyword, keywordType, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(new PagedResponse<>(productPage));
    }

}
