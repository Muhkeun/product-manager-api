package com.muhkeun.productmanagerapi.controller;

import com.muhkeun.productmanagerapi.model.dto.OptionTemplateResponse;
import com.muhkeun.productmanagerapi.service.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/option-templates")
@RequiredArgsConstructor
public class OptionTemplateController {

    private final ProductOptionService productOptionService;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "상품 선택옵션 템플릿 조회", description = "상품 선택옵션 템플릿을 전체 조회한다.")
    public ResponseEntity<List<OptionTemplateResponse>> getAllOptionTemplates() {
        return ResponseEntity.ok(productOptionService.getAllOptionTemplates());
    }
}
