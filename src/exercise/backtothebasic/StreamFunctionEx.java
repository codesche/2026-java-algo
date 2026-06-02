package exercise.backtothebasic;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Java Stream & 함수형 사고 — 중급 예제
 *
 * 주제: 주문 데이터 분석 파이프라인
 * - map / filter / flatMap / reduce
 * - Collector 활용 (groupingBy, partitioningBy, toMap)
 * - 함수 합성 (Function.andThen, compose)
 * - Optional 체이닝
 * - 커스텀 Collector
 */

public class StreamFunctionEx {

    // ── 모델 ──────────────────────────────────────────────────
    enum Category { ELECTRONICS, CLOTHING, FOOD }

    record Product (String name, Category category, double price) {}

    record OrderItem(Product product, int quantity) {
        double subtotal() {
            return product.price() * quantity;
        }
    }

    record Order(String orderId, String customerId, List<OrderItem> items, boolean isPaid) {
        double total() {
            return items.stream().mapToDouble(OrderItem::subtotal).sum();
        }
    }

    // ── 샘플 데이터 ────────────────────────────────────────────
    static List<Order> sampleOrders() {
        var laptop   = new Product("Laptop",   Category.ELECTRONICS, 1200.0);
        var phone    = new Product("Phone",    Category.ELECTRONICS,  800.0);
        var shirt    = new Product("Shirt",    Category.CLOTHING,      50.0);
        var pants    = new Product("Pants",    Category.CLOTHING,      80.0);
        var bread    = new Product("Bread",    Category.FOOD,           3.5);
        var coffee   = new Product("Coffee",   Category.FOOD,           8.0);

        return List.of(
            new Order("O-001", "C-1", List.of(
                new OrderItem(laptop, 1), new OrderItem(coffee, 2)), true),
            new Order("O-002", "C-2", List.of(
                new OrderItem(shirt, 3), new OrderItem(pants, 1)), true),
            new Order("O-003", "C-1", List.of(
                new OrderItem(phone, 1), new OrderItem(bread, 5)), false),
            new Order("O-004", "C-3", List.of(
                new OrderItem(coffee, 10), new OrderItem(bread, 10)), true),
            new Order("O-005", "C-2", List.of(
                new OrderItem(laptop, 2)), false)
        );
    }

    // ── 1. 기본 파이프라인: filter → map → sorted ──────────────

    /**
     * 결제 완료된 주문 중 총액 상위 N개 orderId 반환
     * 사고 포인트: 파이프라인은 "데이터 변환 흐름"이다.
     *              각 단계가 어떤 타입을 내보내는지 추적하라.
     */
    static List<String> topPaidOrderIds(List<Order> orders, int topN) {
        return orders.stream()
            .filter(Order::isPaid)                          // Stream<Order>
            .sorted(Comparator.comparingDouble(Order::total).reversed())
            .limit(topN)                                    // Stream<Order>
            .map(Order::orderId)                            // Stream<String>
            .toList();
    }

    // ── 2. flatMap: 중첩 구조 펼치기 ──────────────────────────

    /**
     * 모든 주문에서 팔린 제품 목록 추출 (중복 포함)
     * 사고 포인트: flatMap은 "중첩 스트림을 평탄화"한다.
     *              Order → List<OrderItem> → Product 경로를 그려보라.
     */
    static List<Product> allSoldProducts(List<Order> orders) {
        return orders.stream()
            .flatMap(order -> order.items().stream())   // Stream<OrderItem>
            .map(OrderItem::product)        // Stream<Product>
            .toList();
    }

    // ── 3. groupingBy: 카테고리별 매출 집계 ───────────────────

    /**
     * 결제 완료 주문 기준, 카테고리별 총 매출 계산
     * 사고 포인트: groupingBy의 두 번째 인수(downstream collector)를
     *              바꾸면 집계 방식이 완전히 달라진다.
     */
    static Map<Category, Double> revenueByCategory(List<Order> orders) {
        return orders.stream()
            .filter(Order::isPaid)
            .flatMap(o -> o.items().stream())
            .collect(Collectors.groupingBy(
                item -> item.product().category(),
                Collectors.summingDouble(OrderItem::subtotal)
            ));
    }

    // ── 4. partitioningBy: 고가 주문 분류 ─────────────────────

    /**
     * 주문을 "고가(>= 기준)" / "일반"으로 분리
     * 사고 포인트: partitioningBy는 groupingBy의 boolean 특수형이다.
     *              결과가 항상 true/false 두 버킷임을 보장한다.
     */
    static Map<Boolean, List<Order>> partitionByValue(List<Order> orders, double threshold) {
        return orders.stream()
            .collect(Collectors.partitioningBy(o -> o.total() >= threshold));
    }

    // ── 5. toMap: 고객별 최고 주문 1건 ────────────────────────

    /**
     * 고객 ID → 해당 고객의 최고액 주문
     * 사고 포인트: toMap의 세 번째 인수(merge function)는
     *              키 충돌 시 어떤 값을 남길지 결정한다.
     */
    static Map<String, Order> topOrderPerCustomer(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.toMap(
                Order::customerId,
                order -> order,
                (existing, incoming) ->
                    existing.total() >= incoming.total() ? existing : incoming
            ));
    }

    // ── 6. 함수 합성: Function.andThen / compose ───────────────

    /**
     * 가격 변환 함수들을 조합 (할인 → 세금 적용)
     * 사고 포인트: andThen은 f → g 순서, compose는 g → f 순서.
     *              파이프라인 방향을 헷갈리지 마라.
     */
    static void demonstrateFunctionComposition() {
        Function<Double, Double> discount10 = price -> price * 0.9;
        Function<Double, Double> tax10      = price -> price * 1.1;

        // andThen: 할인 먼저, 그 다음 세금
        Function<Double, Double> discountThenTax = discount10.andThen(tax10);

        // compose: tax10 안에서 discount10을 먼저 실행 (andThen의 역방향)
        Function<Double, Double> sameThing = tax10.compose(discount10);

        double original = 1000.0;
        System.out.printf("원가: %.1f%n", original);
        System.out.printf("andThen (할인→세금): %.1f%n", discountThenTax.apply(original));
        System.out.printf("compose (tax∘discount): %.1f%n", sameThing.apply(original));
    }

    // ── 7. Optional 체이닝 ────────────────────────────────────

    /**
     * 특정 고객의 최고 주문에서 가장 비싼 품목 이름 반환
     * 사고 포인트: Optional은 "null을 타입으로 표현"한다.
     *              체이닝 중 한 곳이라도 비어 있으면 전체가 empty로 단락된다.
     */
    static Optional<String> mostExpensiveItemNameFor(List<Order> orders, String customerId) {
        return orders.stream()
            .filter(o -> o.customerId().equals(customerId))
            .max(Comparator.comparingDouble(Order::total))  // Optional<Order>
            .map(order -> order.items().stream()
                .max(Comparator.comparingDouble(OrderItem::subtotal)))
            .flatMap(opt -> opt)                            // Optional<OrderItem>
            .map(item -> item.product().name());            // Optional<String>
    }

    // ── 8. 커스텀 Collector: 가중 평균 ────────────────────────

    /**
     * 팔린 제품들의 판매 수량 가중 평균 가격 계산
     * 사고 포인트: Collector.of()로 누적기(accumulator)를 직접 정의하면
     *              어떤 집계든 표현 가능하다.
     */
    static double weightedAveragePrice(List<Order> orders) {
        record Accumulator(double totalRevenue, long totalQty) {
            Accumulator add(OrderItem item) {
                return new Accumulator(
                    totalRevenue + item.subtotal(),
                    totalQty + item.quantity()
                );
            }
            Accumulator merge(Accumulator other) {
                return new Accumulator(
                    totalRevenue + other.totalRevenue,
                    totalQty + other.totalQty
                );
            }
            double result() { return totalQty == 0 ? 0 : totalRevenue / totalQty; }
        }

        Collector<OrderItem, ?, Double> weightedAvg = Collector.of(
            () -> new Accumulator(0, 0),
            Accumulator::add,
            Accumulator::merge,
            Accumulator::result
        );

        return orders.stream()
            .flatMap(o -> o.items().stream())
            .collect(weightedAvg);
    }

    // ── main ──────────────────────────────────────────────────
    public static void main(String[] args) {
        List<Order> orders = sampleOrders();

        System.out.println("=== 1. 결제 완료 상위 2개 주문 ===");
        topPaidOrderIds(orders, 2).forEach(System.out::println);

        System.out.println("\n=== 2. 판매된 모든 제품 (중복 포함) ===");
        allSoldProducts(orders).stream()
            .map(Product::name)
            .forEach(System.out::println);

        System.out.println("\n=== 3. 카테고리별 매출 ===");
        revenueByCategory(orders).forEach((cat, rev) ->
            System.out.printf("  %-15s $%.2f%n", cat, rev));

        System.out.println("\n=== 4. 고가 주문 분류 (기준: $500) ===");
        var partitioned = partitionByValue(orders, 500);
        System.out.println("  고가 주문: " + partitioned.get(true).stream()
            .map(Order::orderId).toList());
        System.out.println("  일반 주문: " + partitioned.get(false).stream()
            .map(Order::orderId).toList());

        System.out.println("\n=== 5. 고객별 최고 주문 ===");
        topOrderPerCustomer(orders).forEach((cid, o) ->
            System.out.printf("  %s → %s ($%.1f)%n", cid, o.orderId(), o.total()));

        System.out.println("\n=== 6. 함수 합성 ===");
        demonstrateFunctionComposition();

        System.out.println("\n=== 7. C-1 고객의 최고 주문 중 가장 비싼 품목 ===");
        mostExpensiveItemNameFor(orders, "C-1")
            .ifPresentOrElse(
                name -> System.out.println("  " + name),
                () -> System.out.println("  (없음)")
            );

        System.out.println("\n=== 8. 수량 가중 평균 단가 ===");
        System.out.printf("  $%.2f%n", weightedAveragePrice(orders));
    }



}
