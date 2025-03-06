package com.muhkeun.productmanagerapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionValue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("옵션값")
    @Column(nullable = false)
    private String optionValue;

    @Comment("옵션 추가 금액")
    private Integer additionalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOption productOption;

    @Builder
    public ProductOptionValue(ProductOption productOption, String optionValue, Integer additionalPrice) {
        this.productOption = productOption;
        this.optionValue = optionValue;
        this.additionalPrice = additionalPrice;
    }
}
