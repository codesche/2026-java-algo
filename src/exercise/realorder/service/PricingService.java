package exercise.realorder.service;

import exercise.realorder.domain.Order;
import exercise.realorder.domain.Product;
import exercise.realorder.repository.ProductRepository;

/**
 * 가격 계산 서비스
 * - 비즈니스 로직 담당
 * - O(n) 알고리즘
 */
public class PricingService {

    private final ProductRepository productRepository;

    public PricingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 총 가격 계산
     *
     * 시간복잡도:
     * - 아이템 순회: O(n)
     * - 상품 조회: O(1)
     * - 전체: O(n)
     */
    public int calculateTotalPrice(Order order) {
        return order.getItems().stream()
            .mapToInt(item -> {
                Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
                return product.getPrice() * item.getQuantity();
            }).sum();
    }

}
