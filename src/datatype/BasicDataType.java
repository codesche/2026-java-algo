package datatype;

import java.util.*;

/**
 * Java 자료구조 몸풀기
 * - List, Stack, Queue, Map, Set, PriorityQueue 핵심 사용법
 */

public class BasicDataType {

    public static void main(String[] args) {
        demoList();
        demoStack();
        demoQueue();
        demoMap();
        demoSet();
        demoPriorityQueue();
    }

    // ── 1. ArrayList ──────────────────────────────────────────────
    static void demoList() {
        System.out.println("=== ArrayList ===");

        List<String> fruits = new ArrayList<>(List.of("banana", "apple", "cherry"));
        fruits.add("date");
        fruits.remove("banana");
        Collections.sort(fruits);

        System.out.println("sorted : " + fruits);
        System.out.println("index 1: " + fruits.get(1));
        System.out.println("size   : " + fruits.size());
        System.out.println("contains apple: " + fruits.contains("apple"));
        System.out.println();
    }

    // ── 2. Stack ──────────────────────────────────────────────────
    static void demoStack() {
        System.out.println("=== Stack ===");

        Deque<Integer> stack = new ArrayDeque<>();
        for (int n : new int[]{10, 20, 30, 40}) stack.push(n);

        System.out.println("peek : " + stack.peek());
        System.out.println("pop  : " + stack.pop());
        System.out.println("pop  : " + stack.pop());
        System.out.println("left : " + stack);
        System.out.println();
    }

    // ── 3. Queue ──────────────────────────────────────────────────
    static void demoQueue() {
        System.out.println("=== Queue ===");

        Queue<String> queue = new LinkedList<>(List.of("first", "second", "third"));
        queue.offer("fourth");

        System.out.println("peek  : " + queue.peek());
        System.out.println("poll  : " + queue.poll());
        System.out.println("poll  : " + queue.poll());
        System.out.println("left  : " + queue);
        System.out.println();
    }

    // ── 4. HashMap ────────────────────────────────────────────────
    static void demoMap() {
        System.out.println("=== HashMap ===");

        Map<String, Integer> score = new HashMap<>();
        score.put("Alice", 90);
        score.put("Bob",   75);
        score.put("Carol", 88);

        score.put("Bob", score.get("Bob") + 5);          // 업데이트
        score.putIfAbsent("Dave", 60);                   // 없을 때만 삽입

        score.forEach((name, s) ->
            System.out.printf("  %-6s → %d%n", name, s));

        String top = Collections.max(score.entrySet(),
            Map.Entry.comparingByValue()).getKey();
        System.out.println("top scorer: " + top);
        System.out.println();
    }

    // ── 5. HashSet / LinkedHashSet ────────────────────────────────
    static void demoSet() {
        System.out.println("=== Set ===");

        List<Integer> nums = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
        Set<Integer> unique  = new HashSet<>(nums);
        Set<Integer> ordered = new TreeSet<>(nums);

        System.out.println("original : " + nums);
        System.out.println("HashSet  : " + unique);   // 순서 무관
        System.out.println("TreeSet  : " + ordered);  // 정렬됨
        System.out.println("dup count: " + (nums.size() - unique.size()));
        System.out.println();
    }

    // ── 6. PriorityQueue (min-heap) ───────────────────────────────
    static void demoPriorityQueue() {
        System.out.println("=== PriorityQueue ===");

        // 작업 큐: [우선순위, 이름]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{3, 0}); // priority=3, id=0 (low)
        pq.offer(new int[]{1, 1}); // priority=1, id=1 (urgent)
        pq.offer(new int[]{2, 2}); // priority=2, id=2 (normal)
        pq.offer(new int[]{1, 3}); // priority=1, id=3 (urgent)

        String[] names = {"LOW", "URGENT-A", "NORMAL", "URGENT-B"};
        System.out.println("처리 순서:");
        while (!pq.isEmpty()) {
            int[] task = pq.poll();
            System.out.printf("  priority=%d  name=%s%n", task[0], names[task[1]]);
        }
        System.out.println();
    }

}
