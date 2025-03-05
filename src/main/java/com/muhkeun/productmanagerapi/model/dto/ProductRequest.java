package com.muhkeun.productmanagerapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    @Schema(description = "상품명", example = "국물 포장용기")
    private String name;

    @NotBlank(message = "상품 설명은 필수 입력값입니다.")
    @Schema(description = "상품 설명", example = "배달용 국물 포장용기입니다.")
    private String description;

    @NotNull(message = "단가는 필수 입력값입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    @Schema(description = "단가", example = "40.6")
    private BigDecimal unitPrice;

    @NotNull(message = "배송비는 필수 입력값입니다.")
    @Min(value = 0, message = "배송비는 0 이상이어야 합니다.")
    @Schema(description = "배송비", example = "3000")
    private BigDecimal shippingFee;
}
