package com.muhkeun.productmanagerapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductOptionValueRequest {

    @NotBlank
    @Schema(description = "옵션 값", example = "선물 포장")
    private String optionValue;

    @Schema(description = "추가 금액", example = "500")
    private int additionalPrice;

}
