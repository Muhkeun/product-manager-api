package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.constant.OptionType;
import com.muhkeun.productmanagerapi.model.entity.ProductOption;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionResponse {

    @Schema(description = "옵션 ID")
    private Long id;

    @Schema(description = "옵션명")
    private String name;

    @Schema(description = "옵션 타입 (선택형/입력형)")
    private OptionType type;

    @Schema(description = "필수 옵션 여부")
    private Boolean isRequired;
    @Schema(description = "옵션 설명")
    private String description;
    @Schema(description = "입력 옵션 추가 금액")
    private Integer inputAdditionalPrice;

    @Schema(description = "옵션 값 목록")
    private List<ProductOptionValueResponse> values;

    public ProductOptionResponse(ProductOption productOption) {
        this.id = productOption.getId();
        this.name = productOption.getName();
        this.type = productOption.getOptionType();
        this.description = productOption.getDescription();
        this.isRequired = productOption.getIsRequired();
        this.inputAdditionalPrice = productOption.getInputAdditionalPrice();
        this.values = productOption.getValues().stream().map(ProductOptionValueResponse::new).toList();
    }
}
