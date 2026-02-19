package theory;

/**
 * "주문 데이터 분석 시스템" 만들기
 * 1. 주문 목록을 분석
 * 2. 고객별 총 구매금액 계산
 * 3. 가장 많이 팔린 상품 찾기
 * 4. VIP 고객 추출
 * 5. 평균 주문 금액 계산
 * 6. 정렬 & 그룹화 & 필터링 포함
 *
 * --------------------------------
 * 주목해서 봐야할 것
 * 1. 왜 불변 객체를 썼는가?
 * 2. Stream이 내부적으로 어떻게 동작할까?
 * 3. Comparator 체이닝은 왜 필요한가?
 * 4. groupingBy는 내부적으로 어떤 자료구조를 만들까?
 * 5. Optional은 왜 사용하는가?
 */

/**
 * | 개념             | 다른 언어 대응                         |
 * | -------------- | -------------------------------- |
 * | Stream         | Python generator / JS map/filter |
 * | groupingBy     | Python defaultdict               |
 * | Optional       | Kotlin null-safe                 |
 * | Comparator 체이닝 | Python sorted(key=tuple)         |
 * | 불변 객체          | Functional Programming           |
 * | Lambda         | 모든 현대 언어 공통                      |
 */

import java.util.*;
import java.util.stream.*;

public class OrderProgram {

    public static void main(String[] args) {

        // 1. 불변 객체 Order
        class Order {
            private final String customer;
            private final String product;
            private final int quantity;
            private final double price;

            public Order(String customer, String product, int quantity, double price) {
                this.customer = customer;
                this.product = product;
                this.quantity = quantity;
                this.price = price;
            }

            public String getCustomer() {
                return customer;
            }

            public String getProduct() {
                return product;
            }

            public int getQuantity() {
                return quantity;
            }

            public double totalPrice() {
                return quantity * price;
            }

            @Override
            public String toString() {
                return customer + " - " + product + " (" + quantity + ")";
            }
        }

        // 2. 샘플 데이터
        List<Order> orders = List.of(
            new Order("Kim", "Laptop", 1, 1200),
            new Order("Lee", "Mouse", 3, 20),
            new Order("Kim", "Keyboard", 2, 50),
            new Order("Park", "Monitor", 2, 300),
            new Order("Lee", "Laptop", 1, 1200),
            new Order("Choi", "Mouse", 5, 20)
        );

        // 3. 고객별 총 구매금액 계산
        Map<String, Double> totalByCustomer =
            orders.stream()
                .collect(Collectors.groupingBy(
                    Order::getCustomer,
                    Collectors.summingDouble(Order::totalPrice)
                ));

        System.out.println("고객별 총 구매금액: ");
        totalByCustomer.forEach((k, v) -> System.out.println(k + " = " + v));

        // 4. 가장 많이 팔린 상품 찾기
        Optional<String> mostSoldProduct =
            orders.stream()
                .collect(Collectors.groupingBy(
                    Order::getProduct,
                    Collectors.summingInt(Order::getQuantity)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        System.out.println("\n가장 많이 팔린 상품");
        mostSoldProduct.ifPresent(System.out::println);

        // 5. 평균 주문 금액
        double averageOrderAmount =
            orders.stream()
                .mapToDouble(Order::totalPrice)
                .average()
                .orElse(0);

        System.out.println("\n평균 주문 금액: " + averageOrderAmount);

        // 6. VIP 고객 (총 구매금액 1500 이상)
        List<String> vipCustomers =
            totalByCustomer.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 1500)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("\nVIP 고객: ");
        vipCustomers.forEach(System.out::println);

        // 7. 주문을 금액 내림차순, 수량 오름차순으로 정렬
        List<Order> sortedOrders =
            orders.stream()
                .sorted(
                    Comparator.comparing(Order::totalPrice).reversed()
                        .thenComparing(Order::getQuantity)
                )
                .collect(Collectors.toList());

        System.out.println("\n정렬된 주문 목록: ");
        sortedOrders.forEach(System.out::println);

        // 8. 예외 처리 흐름 실습 (존재하지 않는 고객 조회)
        String searchCustomer = "Hong";

        try {
            double result = totalByCustomer.getOrDefault(searchCustomer,
                throwIfNotFound(searchCustomer));
            System.out.println(result);
        } catch (RuntimeException e) {
            System.out.println("\n예외 발생: " + e.getMessage());
        }
    }

    private static double throwIfNotFound(String name) {
        throw new RuntimeException("고객을 찾을 수 없습니다: " + name);
    }

}

