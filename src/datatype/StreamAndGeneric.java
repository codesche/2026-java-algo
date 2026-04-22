package datatype;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class StreamAndGeneric {

    // ── 제네릭 클래스 ─────────────────────────────────────────
    static class Box<T> {
        private T value;

        Box(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }

        void set(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    // 제네릭 인터페이스
    interface Transformer<T, R> {
        R transform(T input);
    }

    // 제네릭 메서드
    static <T extends Comparable<T>> T findMax(List<T> list) {
        return list.stream()
            .max(Comparator.naturalOrder())
            .orElseThrow(() -> new NoSuchElementException("리스트가 비어 있습니다."));
    }

    static <T> List<T> filterItems(List<T> list, Predicate<T> condition) {
        return list.stream()
            .filter(condition)
            .collect(Collectors.toList());
    }

    // ── 와일드카드 예시 ────────────────────────────────────────
    static double sumOfNumbers(List<? extends Number> numbers) {
        return numbers.stream()
            .mapToDouble(Number::doubleValue)
            .sum();
    }

    // ── 데이터 클래스 (Stream 실습용) ──────────────────────────
    static class Product {
        String name;
        String category;
        double price;
        int stock;

        Product(String name, String category, double price, int stock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        String getName()     { return name; }
        String getCategory() { return category; }
        double getPrice()    { return price; }
        int getStock()    { return stock; }

        @Override
        public String toString() {
            return String.format("Product{%s, %s, %.0f원, 재고:%d}", name, category, price, stock);
        }
    }

    public static void main(String[] args) {

        // ① 제네릭 클래스 사용
        System.out.println("=== ① 제네릭 클래스 Box<T> ===");
        Box<String>  strBox = new Box<>("Hello Generics");
        Box<Integer> intBox = new Box<>(42);
        System.out.println(strBox);
        System.out.println(intBox);
        intBox.set(100);
        System.out.println("값 변경 후: " + intBox);

        // ② 제네릭 인터페이스 (람다로 구현)
        System.out.println("\n=== ② 제네릭 인터페이스 Transformer<T,R> ===");
        Transformer<String, Integer> lengthOf = String::length;
        Transformer<Integer, String> toLabel  = n -> "번호:" + n;
        System.out.println("\"Java\" 길이 → " + lengthOf.transform("Java"));
        System.out.println("7 → " + toLabel.transform(7));

        // ③ 제네릭 메서드
        System.out.println("\n=== ③ 제네릭 메서드 findMax / filterItems ===");
        List<Integer> nums = Arrays.asList(3, 17, 5, 9, 2, 14);
        System.out.println("최댓값: " + findMax(nums));

        List<String> words = Arrays.asList("apple", "banana", "cherry", "avocado", "blueberry");
        List<String> aWords = filterItems(words, w -> w.startsWith("a"));
        System.out.println("'a'로 시작: " + aWords);

        // ④ 와일드카드
        System.out.println("\n=== ④ 와일드카드 <? extends Number> ===");
        List<Integer> ints    = Arrays.asList(1, 2, 3, 4, 5);
        List<Double>  doubles = Arrays.asList(1.5, 2.5, 3.0);
        System.out.printf("정수 합계: %.1f%n", sumOfNumbers(ints));
        System.out.printf("실수 합계: %.1f%n", sumOfNumbers(doubles));

        // ⑤ Stream 기본 - filter / map / collect
        System.out.println("\n=== ⑤ Stream: filter / map / collect ===");
        List<Product> products = Arrays.asList(
            new Product("노트북",   "전자",  1_200_000, 10),
            new Product("마우스",   "전자",     35_000, 50),
            new Product("책상",     "가구",    450_000,  5),
            new Product("모니터",   "전자",    550_000,  8),
            new Product("의자",     "가구",    300_000, 12),
            new Product("키보드",   "전자",     89_000, 30),
            new Product("책장",     "가구",    200_000,  3)
        );

        List<String> expensiveElec = products.stream()
            .filter(p -> p.getCategory().equals("전자"))
            .filter(p -> p.getPrice() >= 100_000)
            .map(Product::getName)
            .collect(Collectors.toList());
        System.out.println("10만원 이상 전자제품: " + expensiveElec);

        // ⑥ Stream - sorted / limit / forEach
        System.out.println("\n=== ⑥ Stream: sorted / limit / forEach ===");
        System.out.println("가격 상위 3개:");
        products.stream()
            .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
            .limit(3)
            .forEach(p -> System.out.println("  " + p));

        // ⑦ Stream - reduce
        System.out.println("\n=== ⑦ Stream: reduce ===");
        double totalValue = products.stream()
            .reduce(0.0,
                (acc, p) -> acc + p.getPrice() * p.getStock(),
                Double::sum);
        System.out.printf("전체 재고 금액: %,.0f원%n", totalValue);

        // ⑧ Stream - groupingBy
        System.out.println("\n=== ⑧ Stream: groupingBy ===");
        Map<String, List<Product>> byCategory =
            products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        byCategory.forEach((cat, list) -> {
            System.out.println("[" + cat + "]");
            list.forEach(p -> System.out.println("  " + p.getName() + " - " + (int)p.getPrice() + "원"));
        });

        // ⑨ Stream - 통계 (IntSummaryStatistics)
        System.out.println("\n=== ⑨ Stream: 통계 ===");
        IntSummaryStatistics stockStats = products.stream()
            .mapToInt(Product::getStock)
            .summaryStatistics();
        System.out.println("재고 통계 → 합계:" + stockStats.getSum()
            + " / 평균:" + String.format("%.1f", stockStats.getAverage())
            + " / 최소:" + stockStats.getMin()
            + " / 최대:" + stockStats.getMax());

        // ⑩ Stream - flatMap
        System.out.println("\n=== ⑩ Stream: flatMap ===");
        List<List<Integer>> nested = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6, 7, 8, 9)
        );
        List<Integer> flat = nested.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("중첩 리스트 평탄화: " + flat);

        // ⑪ 제네릭 + Stream 조합
        System.out.println("\n=== ⑪ 제네릭 Box 리스트를 Stream으로 처리 ===");
        List<Box<Integer>> boxes = IntStream.rangeClosed(1, 5)
            .mapToObj(Box::new)
            .collect(Collectors.toList());

        int sumOfBoxes = boxes.stream()
            .mapToInt(Box::get)
            .sum();
        System.out.println("Box 리스트: " + boxes);
        System.out.println("합계: " + sumOfBoxes);

        // ⑫ Optional 과 Stream 연계
        System.out.println("\n=== ⑫ Optional + Stream ===");
        Optional<Product> cheapest = products.stream()
            .filter(p -> p.getCategory().equals("가구"))
            .min(Comparator.comparingDouble(Product::getPrice));
        cheapest.ifPresentOrElse(
            p -> System.out.println("가장 저렴한 가구: " + p),
            ()  -> System.out.println("가구 없음")
        );

        System.out.println("\n✅ 모든 예제 완료");

    }
}
