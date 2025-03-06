package com.muhkeun.productmanagerapi.model.entity;

import com.muhkeun.productmanagerapi.constant.OptionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("옵션명")
    @Column(nullable = false)
    private String name;

    @Comment("옵션 설명")
    private String description;

    @Comment("옵션 타입")
    @Column(nullable = false)
    private OptionType optionType;

    @Comment("필수 옵션 여부")
    @Column(nullable = false)
    private Boolean isRequired;

    @Comment("입력 옵션 추가 금액")
    private Integer inputAdditionalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionValue> values = new ArrayList<>();

    @Builder
    public ProductOption(Product product, List<ProductOptionValue> values, String name, String description, OptionType optionType, Boolean isRequired,
                         Integer inputAdditionalPrice) {

        this.product = product;
        this.values = values;
        this.name = name;
        this.description = description;
        this.optionType = optionType;
        this.isRequired = isRequired;
        this.inputAdditionalPrice = inputAdditionalPrice;
    }
}
