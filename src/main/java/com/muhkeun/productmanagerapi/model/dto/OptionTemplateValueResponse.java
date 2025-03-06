package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.model.entity.OptionTemplateValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionTemplateValueResponse {

    private Long id;

    @Schema(description = "옵션 값")
    private String optionValue;

    public OptionTemplateValueResponse(OptionTemplateValue value) {
        this.id = value.getId();
        this.optionValue = value.getOptionValue();
    }
}
