package exercise.hotbusinesslogic.entity;

import exercise.hotbusinesslogic.status.OrderStatus;

/**
 * 주문 도메인
 */
public class Order {

    private final String orderId;
    private final Product product;
    private final int quantity;
    private final int totalPrice;
    private OrderStatus status;
    private Payment payment;

    public Order(String orderId, Product product, int quantity, int totalPrice) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.CREATED;
    }

    public void complete(Payment payment) {
        this.payment = payment;
        this.status = OrderStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId='" + orderId + '\'' +
            ", product=" + product.getName() +
            ", quantity=" + quantity +
            ", totalPrice=" + totalPrice +
            ", status=" + status +
            '}';
    }
}
