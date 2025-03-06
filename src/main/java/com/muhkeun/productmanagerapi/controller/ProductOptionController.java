package com.muhkeun.productmanagerapi.controller;

import com.muhkeun.productmanagerapi.model.dto.ProductOptionRequest;
import com.muhkeun.productmanagerapi.model.dto.ProductOptionResponse;
import com.muhkeun.productmanagerapi.model.entity.CustomUserDetails;
import com.muhkeun.productmanagerapi.model.validation.ValidProductOptions;
import com.muhkeun.productmanagerapi.service.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @Operation(summary = "상품 옵션 전체 조회", description = "상품 옵션을 전체 조회한다.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<ProductOptionResponse>> getProductOptions(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long productId) {
        Long userId = userDetails.getUser().getId();

        return ResponseEntity.ok(productOptionService.getAllProductOptions(userId, productId));
    }

    @Operation(summary = "상품 옵션 전체 업데이트", description = "기존 옵션을 삭제하고 새로운 옵션 목록으로 대체")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping
    public ResponseEntity<Void> replaceProductOptions(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long productId,
            @Valid @RequestBody @ValidProductOptions List<ProductOptionRequest> request) {
        Long userId = userDetails.getUser().getId();

        productOptionService.replaceProductOptions(userId, productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
