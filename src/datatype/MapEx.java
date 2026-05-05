package datatype;

import java.util.*;
import java.util.stream.Collectors;

public class MapEx {

    // 학생 성적 데이터: 이름 → 점수
    private final Map<String, Integer> scoreMap = new LinkedHashMap<>();

    // 학생 학년 데이터: 이름 → 학년
    private final Map<String, String> gradeMap = new HashMap<>();

    public void loadData() {
        scoreMap.put("Alice",   92);
        scoreMap.put("Bob",     75);
        scoreMap.put("Charlie", 88);
        scoreMap.put("Diana",   61);
        scoreMap.put("Edward",  97);
        scoreMap.put("Fiona",   54);
        scoreMap.put("George",  83);

        gradeMap.put("Alice",   "3학년");
        gradeMap.put("Bob",     "1학년");
        gradeMap.put("Charlie", "2학년");
        gradeMap.put("Diana",   "1학년");
        gradeMap.put("Edward",  "3학년");
        gradeMap.put("Fiona",   "2학년");
        gradeMap.put("George",  "1학년");
    }

    // 전체 학생 점수 출력
    public void printAll() {
        System.out.println("=== 전체 학생 성적 ===");
        scoreMap.forEach((name, score) ->
            System.out.printf(" %-10s : %d점%n", name, score)
        );
    }

    // 특정 학생 점수 조회 (기본값 처리 포함)
    public int getScore(String name) {
        return scoreMap.getOrDefault(name, -1);
    }

    // 점수 업데이트 또는 신규 등록
    public void upsertScore(String name, int score) {
        scoreMap.merge(name, score, (oldVal, newVal) -> {
            System.out.printf("  [업데이트] %s: %d → %d%n", name, oldVal, newVal);
            return newVal;
        });
    }

    // 평균 점수 계산
    public double calcAverage() {
        return scoreMap.values().stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
    }

    // 점수 기준 정렬 (내림차순)
    public List<Map.Entry<String, Integer>> sortByScore() {
        return scoreMap.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toList());
    }

    // 합격 (70점 이상) / 불합격 분류
    public void printPassFail() {
        System.out.println("=== 합격 / 불합격 ===");
        Map<Boolean, List<String>> partitioned = scoreMap.entrySet().stream()
            .collect(Collectors.partitioningBy(
                e -> e.getValue() >= 70,
                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
            ));
        System.out.println("  합격: " + partitioned.get(true));
        System.out.println("  불합격: " + partitioned.get(false));
    }

    // 학년별 평균 점수
    public void printAverageByGrade() {
        System.out.println("=== 학년별 평균 점수 ===");
        Map<String, Double> avgByGrade = scoreMap.entrySet().stream()
            .collect(Collectors.groupingBy(
                e -> gradeMap.getOrDefault(e.getKey(), "미분류"),
                Collectors.averagingInt(Map.Entry::getValue)
            ));

        new TreeMap<>(avgByGrade).forEach((grade, avg) ->
            System.out.printf("  %s 평균: %.1f점%n", grade, avg)
        );
    }

    public static void main(String[] args) {
        MapEx processor = new MapEx();

        processor.loadData();
        processor.printAll();

        System.out.println("\n=== 특정 학생 조회 ===");
        System.out.println("  Alice 점수: " + processor.getScore("Alice"));
        System.out.println("  없는학생 점수: " + processor.getScore("없는학생"));

        System.out.println("\n=== 점수 업데이트 ===");
        processor.upsertScore("Bob", 82);
        processor.upsertScore("Hana", 91);   // 신규 등록

        System.out.printf("%n  전체 평균: %.1f점%n", processor.calcAverage());

        System.out.println("\n=== 성적 순위 ===");
        List<Map.Entry<String, Integer>> ranked = processor.sortByScore();
        for (int i = 0; i < ranked.size(); i++) {
            Map.Entry<String, Integer> entry = ranked.get(i);
            System.out.printf("  %d위 %-10s : %d점%n", i + 1, entry.getKey(), entry.getValue());
        }

        System.out.println();
        processor.printPassFail();
        System.out.println();
        processor.printAverageByGrade();
    }

}
