package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.model.entity.OptionTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionTemplateResponse {

    private Long id;

    @Schema(description = "옵션명")
    private String name;

    @Schema(description = "옵션 설명")
    private String description;

    private List<OptionTemplateValueResponse> values;


    public OptionTemplateResponse(OptionTemplate template) {
        this.id = template.getId();
        this.name = template.getName();
        this.description = template.getDescription();
        this.values = template.getValues().stream()
                .map(OptionTemplateValueResponse::new)
                .toList();
    }
}
