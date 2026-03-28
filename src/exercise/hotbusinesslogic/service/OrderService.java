package exercise.hotbusinesslogic.service;

import exercise.hotbusinesslogic.entity.Order;
import exercise.hotbusinesslogic.entity.Payment;
import exercise.hotbusinesslogic.entity.Product;
import exercise.hotbusinesslogic.exception.BusinessException;
import exercise.hotbusinesslogic.repository.ProductRepository;
import java.util.UUID;

/**
 * 주문 서비스 (핵심 비즈니스 로직)
 */
public class OrderService {

    private final ProductRepository productRepository;
    private final PaymentService paymentService;

    public OrderService(ProductRepository productRepository, PaymentService paymentService) {
        this.productRepository = productRepository;
        this.paymentService = paymentService;
    }

    public Order placeOrder(Long productId, int quantity) {

        // 1. 상품 조회
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("상품 없음"));

        // 2. 재고 검증
        if (product.getStock() < quantity) {
            throw new BusinessException("재고 부족");
        }

        // 3. 금액 계산
        int totalPrice = product.getPrice() * quantity;

        // 4. 결제 처리
        Payment payment = paymentService.pay(totalPrice);

        // 5. 재고 차감
        product.decreaseStock(quantity);

        // 6. 주문 생성
        Order order = new Order(UUID.randomUUID().toString(), product, quantity, totalPrice);

        // 7. 상태 변경
        order.complete(payment);

        return order;
    }

}
