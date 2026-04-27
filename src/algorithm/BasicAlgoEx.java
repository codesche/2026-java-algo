package algorithm;

import java.util.*;
import java.util.stream.*;

public class BasicAlgoEx {

    public static void main(String[] args) {
        System.out.println("=== 1. 정렬 및 이분 탐색 (Binary Search)");
        int[] numbers = {5, 2, 9, 1, 3, 8};
        Arrays.sort(numbers);                   // 기본 오름차순 정렬
        System.out.println("정렬된 배열: " + Arrays.toString(numbers));

        // 이분 탐색: 정렬된 상태에서 사용 (O(log N))
        int target = 3;
        int index = Arrays.binarySearch(numbers, target);
        System.out.println("값 " + target + "의 위치: " + index);
        System.out.println();

        System.out.println("=== 2. 우선순위 큐와 힙 (Heap/PriorityQueue) ===");
        // AI 스케줄링이나 데이터 정렬에 자주 사용
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();     // 최소 힙
        minHeap.addAll(Arrays.asList(10, 20, 5, 7));

        System.out.println("Heap에서 꺼낸 순서: ");
        while (!minHeap.isEmpty()) {
            System.out.println(minHeap.poll() + " ");   // 가장 작은 값부터 출력
        }
        System.out.println("\n");

        System.out.println("=== 3. 그리디 알고리즘 (Greedy - 거스름돈) ===");
        int money = 1260;
        int count = 0;
        int[] coinTypes = {500, 100, 50, 10};

        for (int coin : coinTypes) {
            count += money / coin;
            money %= coin;
        }
        System.out.println("최소 동전 개수: " + count);
        System.out.println();

        System.out.println("=== 4. Stack과 Queue (자료구조) ===");
        // Stack (LIFO)
        Stack<String> stack = new Stack<>();
        stack.push("데이터1");
        stack.push("데이터2");
        System.out.println("Stack Pop: " + stack.pop());

        // Queue (FIFO) - LinkedList로 구현
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        System.out.println("Queue Poll: " + queue.poll());
        System.out.println();

        System.out.println("=== 5. 현대적 Java: Stream API 활용 ===");
        List<String> items = Arrays.asList("Apple", "Banana", "Orange", "Avocado", "Blueberry");

        // 'A'로 시작하는 문자열을 대문자로 바꾸고 정렬하여 리스트로 반환
        List<String> filtered = items.stream()
            .filter(s -> s.startsWith("A"))
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());

        System.out.println("Stream 필터링 결과: " + filtered);

        // 숫자 리스트 합계 계산
        int sum = IntStream.rangeClosed(1, 10)          // 1 ~ 10까지 생성
            .filter(n -> n % 2 == 0)                // 짝수만
            .sum();
        System.out.println("1 ~ 10 중 짝수의 합: " + sum);
        System.out.println();

        System.out.println("=== 6. DFS (재귀 기반 탐색 예시) ===");
        boolean[] visited = new boolean[5];

        // 인접 리스트 형식의 간단한 그래프
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).addAll(Arrays.asList(1, 2));
        graph.get(1).add(3);

        System.out.print("DFS 방문 순서: ");
        dfs(0, visited, graph);
        System.out.println();
    }

    // DFS 함수 정의
    static void dfs(int v, boolean[] visited, List<List<Integer>> graph) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i : graph.get(v)) {
            if (!visited[i]) {
                dfs(i, visited, graph);
            }
        }
    }

}
