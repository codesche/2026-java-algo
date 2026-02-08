package theory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapEx {
    public static void main(String[] args) {

        // 1. Map 선언과 생성
        // Key: String, Value: Integer
        Map<String, Integer> scoreMap = new HashMap<>();

        // 2. 데이터 추가 (put)
        scoreMap.put("Alice", 90);
        scoreMap.put("Bob", 85);
        scoreMap.put("Charlie", 95);

        // 같은 key로 put하면 값이 덮어씌워짐
        scoreMap.put("Bob", 88);

        System.out.println("=== 초기 데이터 ===");
        System.out.println(scoreMap);
        System.out.println();

        // 3. 데이터 조회 (get)
        int aliceScore = scoreMap.get("Alice");
        System.out.println("Alice 점수: " + aliceScore);

        // 존재하지 않는 key 조회 시 null 반환
        Integer unknownScore = scoreMap.get("David");
        System.out.println("David 점수: " + unknownScore);
        System.out.println();

        // 4. containsKey / containsValue
        if (scoreMap.containsKey("Charlie")) {
            System.out.println("Charlie는 존재하는 학생입니다.");
        }

        if (scoreMap.containsValue(100)) {
            System.out.println("만점자가 있습니다.");
        } else {
            System.out.println("아직 만점자는 없습니다.");
        }
        System.out.println();

        // 5. null 처리 주의 (실무 중요)
        Integer bobScore = scoreMap.get("Bob");
        if (bobScore != null) {
            System.out.println("Bob 점수: " + bobScore);
        } else {
            System.out.println("Bob 정보 없음");
        }

        // 6. 반복문 순회 (entrySet) - 가장 권장되는 방식
        System.out.println("=== entrySet 순회 ===");
        for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue();
            System.out.println(name + " -> " + score);
        }
        System.out.println();

        // 7. keySet 순회
        System.out.println("=== keySet 순회 ===");
        for (String key : scoreMap.keySet()) {
            System.out.println(key + "의 점수: " + scoreMap.get(key));
        }
        System.out.println();

        // 8. values 순회
        System.out.println("=== values 순회 ===");
        for (int score : scoreMap.values()) {
            System.out.println("점수: " + score);
        }
        System.out.println();

        // 9. 실무 패턴: 기본값 처리 (getOrDefault)
        int davidScore = scoreMap.getOrDefault("David", 0);
        System.out.println("David 점수 (기본값): " + davidScore);
        System.out.println();

        // 10. 실무 패턴: 누적 계산 (빈도 수, 합계 등)
        Map<String, Integer> visitCountMap = new HashMap<>();

        String[] visits = {"home", "about", "home", "contact", "home", "about"};

        for (String page : visits) {
            // 방문 횟수 누적
            visitCountMap.put(page, visitCountMap.getOrDefault(page, 0) + 1);
        }

        System.out.println("=== 페이지 방문 횟수 ===");
        for (Map.Entry<String, Integer> entry : visitCountMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();

        // 11. 데이터 삭제
        scoreMap.remove("Alice");
        System.out.println("Alice 삭제 후: " + scoreMap);
        System.out.println();

        // 12. Map 크기와 비어있는지 확인
        System.out.println("Map 크기: " + scoreMap.size());
        System.out.println("Map 비어있나? " + scoreMap.isEmpty());
        System.out.println();

        // 13. HashMap vs LinkedHashMap vs TreeMap 맛보기
        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("B", 2);
        linkedMap.put("A", 1);
        linkedMap.put("C", 3);
        System.out.println("LinkedHashMap (입력 순서 유지): " + linkedMap);

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("B", 2);
        treeMap.put("A", 1);
        treeMap.put("C", 3);
        System.out.println("TreeMap (정렬됨): " + treeMap);
    }
}
