package exercise.realorder.service;

import exercise.realorder.domain.Order;
import exercise.realorder.util.InputValidator;
import exercise.realorder.util.RateLimiter;

/**
 * 주문 서비스 (Facade 역할)
 * - 전체 흐름 제어
 * - 보안 + 검증 + 비즈니스 로직 연결
 */
public class OrderService {
    private final PricingService pricingService;
    private final RateLimiter rateLimiter;

    public OrderService(PricingService pricingService, RateLimiter rateLimiter) {
        this.pricingService = pricingService;
        this.rateLimiter = rateLimiter;
    }

    /**
     * 주문 처리
     *
     * 처리 순서:
     * 1. Rate Limit 체크 (보안)
     * 2. 입력 검증
     * 3. 가격 계산
     */
    public int processOrder(String userId, Order order) {
        if (!rateLimiter.allowRequest(userId)) {
            throw new RuntimeException("요청이 너무 많습니다. 잠시 후 다시 시도하세요.");
        }

        InputValidator.validateOrder(order);

        return pricingService.calculateTotalPrice(order);
    }

}
