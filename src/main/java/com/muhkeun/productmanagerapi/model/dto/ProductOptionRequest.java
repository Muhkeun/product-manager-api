package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.constant.OptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductOptionRequest {

    @Schema(description = "옵션명", example = "포장 방식")
    @NotBlank(message = "옵션명은 필수입니다.")
    private String name;

    @Schema(description = "옵션 설명", example = "상품의 포장 유형을 선택하세요.")
    private String description;

    @Schema(description = "옵션 타입 (선택형/입력형)", example = "SELECT")
    @NotNull(message = "옵션 타입은 필수입니다.")
    private OptionType type;

    @Schema(description = "필수 옵션 여부", example = "true")
    @NotNull(message = "필수 옵션 여부는 필수입니다.")
    private Boolean isRequired;

    @Schema(description = "입력형 옵션의 추가 금액 (입력형 옵션일 경우 필수)")
    private Integer inputAdditionalPrice;

    @Schema(description = "옵션 값 목록 (선택형 옵션일 경우 필수, 최대 3개)")
    @Valid
    private List<ProductOptionValueRequest> values;
}
