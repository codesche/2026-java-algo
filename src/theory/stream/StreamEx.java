package theory.stream;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

public class StreamEx {

    public static void main(String[] args) {

        List<Order> orders = Arrays.asList(
            new Order(1L, "Kim", "BOOK", 15000, true),
            new Order(2L, "Lee", "FOOD", 20000, false),
            new Order(3L, "Park", "BOOK", 30000, true),
            new Order(4L, "Kim", "ELECTRONICS", 500000, true),
            new Order(5L, "Choi", "FOOD", 10000, false),
            new Order(6L, "Lee", "BOOK", 25000, true)
        );

        // 1. 10,000원 이상 결제된 주문 총 금액 계산
        int totalAmount = orders.stream()
            .filter(order -> order.getAmount() >= 10000)
            .mapToInt(Order::getAmount)
            .sum();

        System.out.println("총 금액: " + totalAmount);

        // 2. Book 카테고리 주문만 추출 후 금액 내림차순 정렬
        List<Order> bookOrders = orders.stream()
            .filter(order -> "BOOK".equals(order.getCategory()))
            .sorted(Comparator.comparing(Order::getAmount).reversed())      // 내림차순 적용
            .collect(toList());

        System.out.println("\n[BOOK 카테고리 내림차순]");
        bookOrders.forEach(System.out::println);

        // 3. 고객별 총 주문 금액 집계 (groupingBy)
        Map<String, Integer> totalByCustomer = orders.stream()
            .collect(groupingBy(
                Order::getCustomerName,
                summingInt(Order::getAmount)
            ));

        System.out.println("\n[고객별 총 주문 금액]");
        totalByCustomer.forEach((k, v) ->
            System.out.println(k + " : " + v)
        );

        // 4. VIP 여부에 따라 분리 (partitioningBy)
        Map<Boolean, List<Order>> vipPartition = orders.stream()
            .collect(partitioningBy(Order::isVip));

        System.out.println("\n[VIP 고객 주문]");
        vipPartition.get(true).forEach(System.out::println);

        // 5. 가장 비싼 주문 찾기 (Optional 처리)
        Optional<Order> maxOrder = orders.stream()
            .max(Comparator.comparing(Order::getAmount));

        maxOrder.ifPresent(order ->
            System.out.println("\n가장 비싼 주문: " + order)
        );

        // 6. 카테고리별 평균 금액 계산
        Map<String, Double> avgByCategory = orders.stream()
            .collect(groupingBy(
                Order::getCategory,
                averagingInt(Order::getAmount)
            ));

        System.out.println("\n[카테고리별 평균 금액]");
        avgByCategory.forEach((k, v) ->
            System.out.println(k + " : " + v)
        );

        // 7. 실무용 패턴 - DTO 변환 (map)
        List<OrderSummary> summaries = orders.stream()
            .map(order -> new OrderSummary(
                order.getCustomerName(),
                order.getAmount()
            ))
            .collect(toList());

        System.out.println("\n[DTO 변환 결과]");
        summaries.forEach(System.out::println);

        // 8. 병렬 스트림 (CPU 작업 많을 때만 사용)
        long count = orders.parallelStream()
            .filter(o -> o.getAmount() > 20000)
            .count();

        System.out.println("\n20000원 초과 주문 수 (parallel): " + count);
    }

}

