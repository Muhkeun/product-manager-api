package com.muhkeun.productmanagerapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.User;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // H2 DB 사용 설정
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        userRepository.deleteAll();

        testUser = userRepository.save(
                User.builder()
                        .email("test@example.com")
                        .password("password")
                        .name("테스트 유저")
                        .build()
        );

        testProducts = productRepository.saveAll(List.of(
                createProduct("일제 주방칼", "스테인리스 일제 주방칼", "5000", "3000"),
                createProduct("독일제 주방가위", "고급형 주방가위", "8000", "4000"),
                createProduct("고급형 포크", "스테인리스 포크", "6000", "3500"),
                createProduct("일회용 국그릇 50개입", "배달용 국그릇", "7000", "2000"),
                createProduct("식당 청소 스펀지", "기름때 제거용 스펀지", "9000", "1500")
        ));
    }

    private Product createProduct(String name, String description, String unitPrice, String shippingFee) {
        return Product.builder()
                .name(name)
                .description(description)
                .unitPrice(new BigDecimal(unitPrice))
                .shippingFee(new BigDecimal(shippingFee))
                .user(testUser)
                .build();
    }

    private Page<ProductResponse> searchProducts(String keyword, SearchKeywordType type, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.searchProductsByUserId(testUser.getId(), keyword, type, minPrice, maxPrice, pageable);
    }

    @Test
    @DisplayName("모든 상품 조회")
    void testSearchProductsByUserId_All() {
        Page<ProductResponse> result = searchProducts(null, null, null, null, PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(testProducts.size());
    }

    @Test
    @DisplayName("주방' 포함된 이름 필터링")
    void testSearchProductsByUserId_WithNameFilter() {
        Page<ProductResponse> result = searchProducts("주방", SearchKeywordType.NAME, null, null, PageRequest.of(0, 10));
        assertThat(result.getContent()).extracting(ProductResponse::getName)
                .containsExactly("일제 주방칼", "독일제 주방가위");
    }

    @Test
    @DisplayName("스테인리스' 포함된 상품설명 필터링")
    void testSearchProductsByUserId_WithDescriptionFilter() {
        Page<ProductResponse> result = searchProducts("스테인리스", SearchKeywordType.DESCRIPTION, null, null, PageRequest.of(0, 10));
        assertThat(result.getContent()).extracting(ProductResponse::getName)
                .containsExactly("일제 주방칼", "고급형 포크");
    }

    @Test
    @DisplayName("가격 범위 (5,000원 이상 7,000원 이하)")
    void testSearchProductsByUserId_WithPriceRange() {
        Page<ProductResponse> result = searchProducts(null, null, new BigDecimal("5000"), new BigDecimal("7000"), PageRequest.of(0, 10));
        assertThat(result.getContent()).extracting(ProductResponse::getName)
                .containsExactly("일제 주방칼", "고급형 포크", "일회용 국그릇 50개입");
    }

    @Test
    @DisplayName("정렬 (가격 오름차순)")
    void testSearchProductsByUserId_WithSortingAsc() {
        Page<ProductResponse> result = searchProducts(null, null, null, null, PageRequest.of(0, 10, Sort.by("unitPrice").ascending()));
        assertThat(result.getContent().get(0).getName()).isEqualTo("일제 주방칼");
        assertThat(result.getContent().get(result.getContent().size() - 1).getName()).isEqualTo("식당 청소 스펀지");
    }

    @Test
    @DisplayName("정렬 (가격 내림차순)")
    void testSearchProductsByUserId_WithSortingDesc() {
        Page<ProductResponse> result = searchProducts(null, null, null, null, PageRequest.of(0, 10, Sort.by("unitPrice").descending()));
        assertThat(result.getContent().get(0).getName()).isEqualTo("식당 청소 스펀지");
        assertThat(result.getContent().get(result.getContent().size() - 1).getName()).isEqualTo("일제 주방칼");
    }
}
