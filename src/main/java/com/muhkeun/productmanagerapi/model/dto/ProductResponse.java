package com.muhkeun.productmanagerapi.model.dto;

import com.muhkeun.productmanagerapi.model.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ProductResponse {

    @Schema(description = "상품 ID")
    private final Long id;

    @Schema(description = "상품명")
    private final String name;

    @Schema(description = "상품 설명")
    private final String description;

    @Schema(description = "단가")
    private final BigDecimal unitPrice;

    @Schema(description = "배송비")
    private final BigDecimal shippingFee;

    @Schema(description = "등록일")
    private final LocalDateTime createdAt;


    @QueryProjection
    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.unitPrice = product.getUnitPrice();
        this.shippingFee = product.getShippingFee();
        this.createdAt = product.getCreatedAt();
    }
}
