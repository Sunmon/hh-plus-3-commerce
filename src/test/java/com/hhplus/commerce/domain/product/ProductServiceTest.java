package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockHistoryRepository;
import com.hhplus.commerce.domain.stock.StockRepository;
import com.hhplus.commerce.domain.stock.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    private ProductService productService;
    @Autowired
    private StockRepository stockRepository;
    private StockServiceImpl stockService;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @BeforeEach
    void beforeEach() {
//        stockRepository = new StockRepositoryMemoryImpl();
//        stockHistoryRepository = new StockHistoryRepositoryMemoryImpl();
        stockService = new StockServiceImpl(stockRepository, stockHistoryRepository);
//        productRepository = new ProductRepositoryMemoryImpl();
        productService = new ProductServiceImpl(productRepository, stockService);
    }

    @DisplayName("상품 ID로 정보 조회 테스트")
    @Test
    void testGetProductInfo() {
        //given
        Long productId = 1L;
        productRepository.save(new Product());
        // when
        Product product = productService.getProductWithStock(productId);
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
        assertThatThrownBy(() -> productService.getProductWithStock(productId))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
