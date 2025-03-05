package com.muhkeun.productmanagerapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.muhkeun.productmanagerapi.constant.SearchKeywordType;
import com.muhkeun.productmanagerapi.model.dto.ProductRequest;
import com.muhkeun.productmanagerapi.model.dto.ProductResponse;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.User;
import com.muhkeun.productmanagerapi.repository.ProductRepository;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private User testUser;
    private Product testProduct;

    private ProductResponse testProductResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .email("test@example.com")
                .password("securePassword")
                .name("테스트 유저")
                .build();

        setUserId(testUser, 1L);

        testProduct = Product.builder()
                .name("테스트 상품")
                .description("테스트 상품 설명")
                .unitPrice(new BigDecimal("10000"))
                .shippingFee(new BigDecimal("2500"))
                .user(testUser)
                .build();

        testProductResponse = new ProductResponse(testProduct);

    }

    @Test
    @DisplayName("상품 등록 성공: 중복되지 않은 상품명")
    void testCreateProductSuccess() {
        // given
        ProductRequest request = new ProductRequest("새로운 상품", "설명", new BigDecimal("12000"), new BigDecimal("3000"));

        when(productRepository.findByUserIdAndName(1L, request.getName())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // when & then
        assertDoesNotThrow(() -> productService.createProduct(testUser, request));

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 등록 실패: 같은 유저의 중복된 상품명")
    void testCreateProductDuplicateNameWithSameUser() {
        // given
        ProductRequest request = new ProductRequest("테스트 상품", "설명", new BigDecimal("12000"), new BigDecimal("3000"));

        when(productRepository.findByUserIdAndName(testUser.getId(), request.getName()))
                .thenReturn(Optional.of(testProduct));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                productService.createProduct(testUser, request)
        );

        assertEquals("이미 존재하는 상품명입니다.", exception.getMessage());
    }


    @Test
    @DisplayName("상품 검색 성공")
    void testSearchProductsByUser() {
        // given
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("unitPrice").ascending());

        when(productRepository.searchProductsByUserId(
                any(Long.class), any(String.class), any(SearchKeywordType.class), any(BigDecimal.class),
                any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(testProductResponse), pageable, 1));

        // when
        Page<ProductResponse> result = productService.searchProducts(
                userId, "테스트", SearchKeywordType.NAME, new BigDecimal("5000"), new BigDecimal("15000"), pageable
        );

        // then
        assertEquals(1, result.getContent().size());
        assertEquals("테스트 상품", result.getContent().get(0).getName());
    }

    private void setUserId(User user, Long id) {
        try {
            Field field = User.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(user, id);
        } catch (Exception e) {
            throw new RuntimeException("User ID 설정 실패", e);
        }
    }
}
