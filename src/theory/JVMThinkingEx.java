package theory;

/**
 * 1. Stream은 Iterator 기반일까 Spliterator 기반일까?
 * 2. groupingBy의 내부 Map은 항상 HashMap일까?
 * 3. Comparator 체이닝이 많아지면 성능 영향은?
 * 4. Map.getOrDefault는 해시를 몇 번 계산할까?
 * 5. 병렬 스트림은 항상 빠를까?
 * 6. Optional 대신 null을 쓰면 어떤 버그가 생길까?
 */

import java.util.*;
import java.util.stream.*;

public class JVMThinkingEx {

    static class Order {
        String customer;
        int amount;

        Order(String customer, int amount) {
            this.customer = customer;
            this.amount = amount;
        }

        // getter
        String getCustomer() {
            return customer;
        }

        // getter
        int getAmount() {
            return amount;
        }
    }

    // 해시 호출 횟수 추적용 Key 클래스
    static class DebugKey {
        String value;
        static int hashCount = 0;

        DebugKey(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            hashCount++;
            return value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof DebugKey)) return false;
            return value.equals(((DebugKey) o).value);
        }
    }

    public static void main(String[] args) {

        List<Order> orders = IntStream.range(0, 1_000_00)
            .mapToObj(i -> new Order("C" + (i % 100), i))
            .collect(Collectors.toList());

        // =========================
        // 1️⃣ Stream은 무엇 기반일까?
        // java.util.ArrayList$ArrayListSpliterator
        // Spliterator 기반
        // =========================
        Spliterator<Order> spliterator = orders.stream().spliterator();
        System.out.println("Spliterator: " + spliterator.getClass().getName());

        // =========================
        // 2️⃣ groupingBy 내부 Map 타입 확인 - TreeMap
        // groupingBy 기본은 HashMap, Supplier 지정하면 바뀜
        // =========================
        Map<String, List<Order>> treeGrouped =
            orders.stream()
                .collect(Collectors.groupingBy(
                    Order::getCustomer,
                    TreeMap::new,
                    Collectors.toList()
                ));

        System.out.println("TreeMap 지정 시 타입: " + treeGrouped.getClass().getName());

        // =========================
        // 3️⃣ Comparator 체이닝 성능 실험
        // 체이닝이 많아질수록 비교 연산 증가, 분기 증가, CPU branch prediction 영향
        // =========================
        List<Order> copy = new ArrayList<>(orders);

        long start1 = System.currentTimeMillis();
        copy.sort(Comparator.comparingInt(o -> o.getAmount()));
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        copy.sort(
            Comparator.comparing(Order::getAmount)
                .thenComparing(Order::getCustomer)
                .thenComparing(o -> o.getAmount() % 10)
        );

        long end2 = System.currentTimeMillis();

        System.out.println("단일 Comparator 시간: " + (end1 - start1));
        System.out.println("체이닝 Comparator 시간: " + (end2 - start2));

        // =========================
        // 4️⃣ getOrDefault 해시 호출 횟수 실험
        // 직접 hashCount 숫자 확인 -> put + getOrDefault 각각 hash 호출
        // =========================
        Map<DebugKey, Integer> debugMap = new HashMap<>();
        DebugKey.hashCount = 0;

        DebugKey key = new DebugKey("test");

        debugMap.put(key, 1);
        debugMap.getOrDefault(key, 0);

        System.out.println("hashCode 호출 횟수: " + DebugKey.hashCount);

        // =========================
        // 5️⃣ 병렬 vs 순차 스트림 성능 비교
        // 병렬 스트림은 항상 빠른가?
        // 1. 데이터 작으면 오히려 느림
        // 2. ForkJoinPool 오버헤드 존재
        // 3. CPU 코어 수 영향
        // =========================
        long s1 = System.currentTimeMillis();
        long sum1 = orders.stream()
            .mapToLong(Order::getAmount)
            .sum();
        long e1 = System.currentTimeMillis();

        long s2 = System.currentTimeMillis();
        long sum2 = orders.parallelStream()
            .mapToLong(Order::getAmount)
            .sum();
        long e2 = System.currentTimeMillis();

        System.out.println("순차 스트림 시간: " + (e1 - s1));
        System.out.println("병렬 스트림 시간: " + (e2 - s2));

        // =========================
        // 6️⃣ Optional vs null 비교
        // null의 위험성 - NPE, 호출 지점에서 터짐, 디버깅 어려움
        // Optional은 타입 단계에서 위험 표현
        // =========================
        try {
            String value = findCustomerWithNull(null);
            System.out.println(value.length());             // NPE 발생 가능
        } catch (Exception e) {
            System.out.println("null 사용 시 예외: " + e);
        }

        Optional<String> optional = findCustomerOptional(null);
        System.out.println("Optional 안전 처리: " + optional.orElse("값 없음"));
    }

    static String findCustomerWithNull(String name) {
        if (name == null) return null;
        return name.toUpperCase();
    }

    static Optional<String> findCustomerOptional(String name) {
        return Optional.ofNullable(name)
            .map(String::toUpperCase);
    }

}
