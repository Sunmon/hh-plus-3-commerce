package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.product.ProductRepository;
import com.hhplus.commerce.domain.product.ProductRepositoryMemoryImpl;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockServiceTest {

    //    @Autowired
    private StockService stockService;
    //    @Autowired
    private StockRepository stockRepository;

    //    @Autowired
    private ProductRepository productRepository;

    private StockHistoryRepository stockHistoryRepository;

    @BeforeEach
    void beforeEach() {
        stockRepository = new StockRepositoryMemoryImpl();
        stockHistoryRepository = new StockHistoryRepositoryMemoryImpl();
        stockService = new StockServiceImpl(stockRepository, stockHistoryRepository);
        productRepository = new ProductRepositoryMemoryImpl();
    }

    @DisplayName("상품 ID로 재고 조회")
    @Test
    void testGetStockByProductId() {
        //given
        Long productId = 1L;
        Product product = Product.of(productId, "상품1", 100L);
        productRepository.insert(product);
        productRepository.insert(new Product());
        stockRepository.insert(Stock.of(1L, product, 100L));
        // when
        Stock stock = stockService.getStockByProductId(productId);
        // then
        assertThat(stock.getProduct().getId()).isEqualTo(productId);
        assertThat(stock.getStock()).isEqualTo(100L);
    }

    @DisplayName("상품 ID로 재고 수량 감소 테스트")
    @Test
    void testDecreaseStockByProductId() {
        //given
        Long productId = 1L;
        Long initQuantity = 100L;
        Long quantity = 10L;
        Long overQuantity = 10000L;
        Product product = Product.of(productId, "상품1", 100L);

        productRepository.insert(product);
        productRepository.insert(new Product());
        stockRepository.insert(Stock.of(1L, product, initQuantity));
        // when
        Stock stock = stockService.decreaseStockByProductId(productId, quantity);
        // then
        assertThat(stock.getProduct().getId()).isEqualTo(productId);
        assertThat(stock.getStock()).isEqualTo(initQuantity - quantity);
        assertThatThrownBy(() -> stockService.decreaseStockByProductId(productId, overQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

}