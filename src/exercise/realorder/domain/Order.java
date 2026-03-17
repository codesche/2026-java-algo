package exercise.realorder.domain;

// 주제: 보안 + 성능을 고려한 주문 처리 시스템
// 목적: 단순 실행이 아닌 "설계 의도"를 이해하는 실습

import java.util.*;

/**
 * 주문 도메인 객체
 * - 불변 객체로 설계 (Immutable)
 * - 외부에서 상태 변경 불가능 -> 안정성 확보
 */
public class Order {

    private final String id;
    private final List<OrderItem> items;

    public Order(String id, List<OrderItem> items) {
        this.id = id;

        // 방어적 복사 + 불변 리스트 적용
        this.items = Collections.unmodifiableList(items);
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

}
