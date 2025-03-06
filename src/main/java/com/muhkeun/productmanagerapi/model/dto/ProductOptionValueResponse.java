package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.model.entity.ProductOptionValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionValueResponse {

    @Schema(description = "옵션 값 ID")
    private Long id;

    @Schema(description = "옵션 값")
    private String optionValue;

    @Schema(description = "추가 금액")
    private int additionalPrice;

    public ProductOptionValueResponse(ProductOptionValue productOptionValue) {
        this.id = productOptionValue.getId();
        this.optionValue = productOptionValue.getOptionValue();
        this.additionalPrice = productOptionValue.getAdditionalPrice();
    }
}
