package exercise.realorder.util;

import exercise.realorder.domain.Order;

/**
 * 입력 검증 클래스
 * - 모든 외부 입력은 반드시 검증
 */
public class InputValidator {

    /**
     * 주문 검증
     */
    public static void validateOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("주문은 null일 수 없습니다.");
        if (order.getItems().isEmpty()) throw new IllegalArgumentException("주문 항목이 비어 있습니다.");
    }

}
