package com.muhkeun.productmanagerapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Comment("상품명")
    private String name;

    @Column(nullable = false)
    @Comment("상품 설명")
    private String description;

    @Column(nullable = false)
    @Comment("단가")
    private BigDecimal unitPrice;

    @Column(nullable = false)
    @Comment("배송비")
    private BigDecimal shippingFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Product(String name, String description, BigDecimal unitPrice, BigDecimal shippingFee, User user) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.shippingFee = shippingFee;
        this.user = user;
    }
}
