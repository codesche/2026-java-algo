package exercise.realorder.main;

import exercise.realorder.domain.Order;
import exercise.realorder.domain.OrderItem;
import exercise.realorder.domain.Product;
import exercise.realorder.repository.InMemoryProductRepository;
import exercise.realorder.service.OrderService;
import exercise.realorder.service.PricingService;
import exercise.realorder.util.RateLimiter;
import java.util.Arrays;
import java.util.List;

/**
 * 실습 포인트:
 * - Repository -> Service -> Domain 흐름 이해
 * - 객체 간 의존 관계 파악
 */
public class MainApp {
    public static void main(String[] args) {

        // 1. Repository 초기화
        InMemoryProductRepository repo = new InMemoryProductRepository();
        repo.save(new Product("A", 100));
        repo.save(new Product("B", 200));

        // 2. 서비스 생성 (의존성 주입)
        PricingService pricingService = new PricingService(repo);
        RateLimiter rateLimiter = new RateLimiter(1000);
        OrderService orderService = new OrderService(pricingService, rateLimiter);

        // 3. 주문 생성
        List<OrderItem> items = Arrays.asList(
            new OrderItem("A", 2),
            new OrderItem("B", 1)
        );

        Order order = new Order("user-1", items);

        // 4. 주문 처리 실행
        int total = orderService.processOrder("user-1", order);

        System.out.println("총 가격: " + total);
    }
}
