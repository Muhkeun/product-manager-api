package com.muhkeun.productmanagerapi.repository;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import com.muhkeun.productmanagerapi.model.dto.QProductResponse;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Slf4j
public class QProductRepositoryImpl implements QProductRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductResponse> searchProductsByUserId(Long userId, String keyword, SearchKeywordType keywordType, BigDecimal minPrice,
                                                        BigDecimal maxPrice, Pageable pageable) {
        QProduct product = QProduct.product;
        BooleanBuilder where = buildWhereClause(userId, keyword, keywordType, minPrice, maxPrice, product);

        List<ProductResponse> results = queryFactory
                .select(new QProductResponse(product))
                .from(product)
                .where(where)
                .orderBy(getSortOrder(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(where)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }

    private BooleanBuilder buildWhereClause(Long userId, String keyword, SearchKeywordType keywordType, BigDecimal minPrice, BigDecimal maxPrice,
                                            QProduct product) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(product.user.id.eq(userId));

        if (keyword != null && keywordType != null) {
            where.and(switch (keywordType) {
                case NAME -> product.name.containsIgnoreCase(keyword);
                case DESCRIPTION -> product.description.containsIgnoreCase(keyword);
            });
        }
        if (minPrice != null) {
            where.and(product.unitPrice.goe(minPrice));
        }
        if (maxPrice != null) {
            where.and(product.unitPrice.loe(maxPrice));
        }
        return where;
    }

    private OrderSpecifier<?>[] getSortOrder(Pageable pageable) {
        PathBuilder<Product> entityPath = new PathBuilder<>(Product.class, "product");

        return pageable.getSort().stream()
                .map(order -> {
                    String property = order.getProperty();
                    try {
                        ComparableExpressionBase<?> path = entityPath.getComparable(property, Comparable.class);
                        return order.isAscending() ? path.asc() : path.desc();
                    } catch (IllegalArgumentException e) {
                        log.error("정렬 불가능한 필드: {}", property, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(OrderSpecifier[]::new);
    }
}
