package exercise.hotbusinesslogic.controller;

import exercise.hotbusinesslogic.entity.Order;
import exercise.hotbusinesslogic.entity.Product;
import exercise.hotbusinesslogic.exception.BusinessException;
import exercise.hotbusinesslogic.repository.ProductRepository;
import exercise.hotbusinesslogic.service.OrderService;
import exercise.hotbusinesslogic.service.PaymentService;

public class OrderApp {

    public static void main(String[] args) {

        ProductRepository productRepository = new ProductRepository();
        PaymentService paymentService = new PaymentService();
        OrderService orderService = new OrderService(productRepository, paymentService);

        // 테스트 데이터
        productRepository.save(new Product(1L, "노트북", 1000, 5));
        productRepository.save(new Product(2L, "마우스", 50, 10));

        try {
            Order order = orderService.placeOrder(1L, 2);
            System.out.println("주문 성공: " + order);
        } catch (BusinessException e) {
            System.out.println("주문 실패: " + e.getMessage());
        }
    }

}
