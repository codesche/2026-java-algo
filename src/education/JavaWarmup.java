package education;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * ┌─────────────────────────────────────────────────┐
 * │         Java 기본기 Warm-up (약 200줄)           │
 * │  1. 자료구조 / 알고리즘                          │
 * │  2. Stream API                                   │
 * │  3. 문자열 처리                                  │
 * └─────────────────────────────────────────────────┘
 */

public class JavaWarmup {

    public static void main(String[] args) {
        System.out.println("===== 1. 자료구조 / 알고리즘 =====\n");
        demoDataStructures();

        System.out.println("\n===== 2. Stream API =====\n");
        demoStreams();

        System.out.println("\n===== 3. 문자열 처리 =====\n");
        demoStrings();
    }

    // ─────────────────────────────────────────────
    //  SECTION 1 : 자료구조 / 알고리즘
    // ─────────────────────────────────────────────

    static void demoDataStructures() {

        /* ── 1-1. Stack : 괄호 유효성 검사 ── */
        String expr1 = "({[]})";
        String expr2 = "({[})";
        System.out.println("[Stack - 괄호검사]");
        System.out.println(expr1 + " → " + (isValidParentheses(expr1) ? "유효" : "무효"));
        System.out.println(expr2 + " → " + (isValidParentheses(expr2) ? "유효" : "무효"));

        /* ── 1-2. Queue : BFS로 최단 경로 ── */
        System.out.println("\n[Queue - BFS 최단 경로]");
        int[][] grid = {
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {1, 0, 0, 0},
            {0, 0, 0, 0}
        };
        int dist = bfsShortestPath(grid, 0, 0, 3, 3);
        System.out.println("(0,0) → (3,3) 최단 거리: " + dist);

        /* ── 1-3. HashMap : 두 수의 합 (Two Sum) ── */
        System.out.println("\n[HashMap - Two Sum]");
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("nums=" + Arrays.toString(nums)
            + ", target=" + target
            + " → 인덱스 " + Arrays.toString(result));

        /* ── 1-4. 정렬 비교 : Bubble Sort vs Arrays.sort ── */
        System.out.println("\n[정렬 - Bubble Sort vs Arrays.sort]");
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr2 = arr1.clone();

        bubbleSort(arr1);
        Arrays.sort(arr2);
        System.out.println("BubbleSort  : " + Arrays.toString(arr1));
        System.out.println("Arrays.sort : " + Arrays.toString(arr2));

        /* ── 1-5. 재귀 : 피보나치 (메모이제이션) ── */
        System.out.println("\n[재귀 + 메모이제이션 - 피보나치]");
        Map<Integer, Long> memo = new HashMap<>();
        System.out.print("fib(0 ~ 10) = ");
        for (int i = 0; i <= 10; i++) {
            System.out.print(fib(i, memo) + (i < 10 ? ", " : "\n"));
        }
    }

    /** 스택으로 괄호 유효성 검사 */
    static boolean isValidParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
        for (char c : s.toCharArray()) {
            if ("([{".indexOf(c) >= 0) {
                stack.push(c);
            } else {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
            }
        }
        return stack.isEmpty();
    }

    /** BFS 최단 경로 (0=이동 가능, 1=벽) */
    static int bfsShortestPath(int[][] grid, int sr, int sc, int er, int ec) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue  = new LinkedList<>();
        queue.offer(new int[]{sr, sc, 0});
        visited[sr][sc] = true;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], d = cur[2];
            if (r == er && c == ec) return d;
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                    && !visited[nr][nc] && grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, d + 1});
                }
            }
        }
        return -1;              // 경로 없음
    }

    /** HashMap으로 Two Sum */
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) return new int[]{map.get(complement), i};
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    /** Bubble Sort */
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
    }

    /** 피보나치 (메모이제이션) */
    static long fib(int n, Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        long val = fib(n - 1, memo) + fib(n - 2, memo);
        memo.put(n, val);
        return val;
    }

    // ─────────────────────────────────────────────
    //  SECTION 2 : Stream API
    // ─────────────────────────────────────────────

    static void demoStreams() {
        List<Integer> numbers = List.of(3, 7, 1, 9, 2, 5, 8, 4, 6, 10);

        /* ── 2-1. filter / map / collect ── */
        System.out.println("[filter + map + collect]");
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("짝수의 제곱(정렬): " + evenSquares);

        /* ── 2-2. reduce : 합계, 곱 ── */
        System.out.println("\n[reduce - 합계 / 곱]");
        int sum = numbers.stream().reduce(0, Integer::sum);
        long product = numbers.stream().mapToLong(Integer::longValue)
            .reduce(1L, (a, b) -> a * b);
        System.out.println("합계: " + sum + ", 곱: " + product);

        /* ── 2-3. groupingBy / counting ── */
        System.out.println("\n[Collectors.groupingBy - 홀짝 분류]");
        Map<String, List<Integer>> grouped = numbers.stream()
            .collect(Collectors.groupingBy(n -> n % 2 == 0 ? "짝수" : "홀수"));
        grouped.forEach((k, v) -> System.out.println(k + ": " + v));

        /* ── 2-4. flatMap : 중첩 리스트 펼치기 ── */
        System.out.println("\n[flatMap - 중첩 리스트 펼치기]");
        List<List<String>> nested = List.of(
            List.of("사과", "바나나"),
            List.of("딸기", "포도", "오렌지"),
            List.of("수박")
        );
        List<String> flat = nested.stream()
            .flatMap(Collection::stream)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("펼친 과일: " + flat);

        /* ── 2-5. Optional + Stream 연계 ── */
        System.out.println("\n[Optional - 최댓값 안전 처리]");
        OptionalInt max = numbers.stream().mapToInt(Integer::intValue).max();

        max.ifPresentOrElse(
            v -> System.out.println("최댓값: " + v),
            () -> System.out.println("비어 있음")
        );

        /* ── 2-6. 사용자 정의 객체 스트림 ── */
        System.out.println("\n[객체 Stream - 평균 점수 top3]");
        List<Student> students = List.of(
            new Student("Alice", 88),
            new Student("Bob",   95),
            new Student("Carol", 72),
            new Student("David", 91),
            new Student("Eva",   85)
        );
        students.stream()
            .sorted(Comparator.comparingInt(Student::score).reversed())
            .limit(3)
            .forEach(s -> System.out.println("  " + s.name() + " : " + s.score()));
    }

    record Student(String name, int score) {}

    // ─────────────────────────────────────────────
    //  SECTION 3 : 문자열 처리
    // ─────────────────────────────────────────────

    static void demoStrings() {

        /* ── 3-1. 팰린드롬 검사 ── */
        System.out.println("[팰린드롬 검사]");
        String[] words = {"racecar", "hello", "madam", "java"};
        for (String w : words)
            System.out.println(w + " → " + (isPalindrome(w) ? "팰린드롬 O" : "팰린드롬 X"));

        /* ── 3-2. 애너그램 검사 ── */
        System.out.println("\n[애너그램 검사]");
        System.out.println("listen / silent → " + isAnagram("listen", "silent"));
        System.out.println("hello  / world  → " + isAnagram("hello", "world"));

        /* ── 3-3. 단어 빈도 카운트 ── */
        System.out.println("\n[단어 빈도 카운트]");
        String sentence = "the quick brown fox jumps over the lazy dog the fox";
        Map<String, Long> freq = Arrays.stream(sentence.split(" "))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(4)
            .forEach(e -> System.out.println("  '" + e.getKey() + "' : " + e.getValue()));

        /* ── 3-4. StringBuilder로 문자열 뒤집기 / 조작 ── */
        System.out.println("\n[StringBuilder - 뒤집기 & 조작]");
        String original = "Hello, Java!";
        String reversed = new StringBuilder(original).reverse().toString();
        System.out.println("원본  : " + original);
        System.out.println("뒤집기: " + reversed);

        /* ── 3-5. 정규식 : 이메일 유효성 검사 ── */
        System.out.println("\n[정규식 - 이메일 유효성 검사]");
        String[] emails = {"user@example.com", "bad-email@", "test.user@mail.org"};
        String   regex  = "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        for (String email : emails)
            System.out.println(email + " → " + (email.matches(regex) ? "유효" : "무효"));

        /* ── 3-6. String.format vs String.join ── */
        System.out.println("\n[String.format & String.join]");
        String joined    = String.join(" | ", "Java", "Stream", "OOP");
        String formatted = String.format("%-10s %-10s %-10s", "항목A", "항목B", "항목C");
        System.out.println("join    : " + joined);
        System.out.println("format  : " + formatted);

        /* ── 3-7. 카멜케이스 → 스네이크케이스 변환 ── */
        System.out.println("\n[카멜케이스 → 스네이크케이스]");
        String[] camelCases = {"helloWorld", "myVariableName", "javaStreamAPI"};
        for (String c : camelCases)
            System.out.println(c + " → " + camelToSnake(c));

    }

    /** 팰린드롬 검사 */
    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    /** 애너그램 검사 */
    static boolean isAnagram(String a, String b) {
        char[] ca = a.toCharArray(), cb = b.toCharArray();
        Arrays.sort(ca);
        Arrays.sort(cb);
        return Arrays.equals(ca, cb);
    }

    /** 카멜케이스 → 스네이크케이스 */
    static String camelToSnake(String camel) {
        return camel
            .replaceAll("([A-Z])", "_$1")
            .toLowerCase()
            .replaceAll("^_", "");
    }

}
