package exercise.realorder.domain;

/**
 * 주문 항목
 * - 하나의 상품 + 수량
 */
public class OrderItem {
    private final String productId;
    private final int quantity;

    public OrderItem(String productId, int quantity) {
        if (quantity <= 0) throw new IllegalStateException("수량은 1 이상이어야 합니다.");
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

}
