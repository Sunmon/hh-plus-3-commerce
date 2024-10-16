package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void beforeEach() {
        productRepository = new ProductRepositoryMemoryImpl();
        productService = new ProductServiceImpl(productRepository);
    }

    @DisplayName("상품 ID로 정보 조회 테스트")
    @Test
    void testGetProductInfo() {
        //given
        Long productId = 1L;
        productRepository.insert(new Product());
        // when
        Product product = productService.getProduct(productId);
        // then
        assertThat(product.getId()).isEqualTo(productId);
    }

    @DisplayName("존재하지 않는 상품 ID로 조회 시도시 오류를 발생시켜야 한다.")
    @Test
    void getWrongProductInfo() {
        //given
        Long productId = 45678L;
        // when
        // then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
