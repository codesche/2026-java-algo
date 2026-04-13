package algorithm;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class MapEx {

    // 1. 캐싱을 위한 LRU 클래스 (내부 정적 클래스)
    static class SimpleCache<K, V> extends LinkedHashMap<K, V> {
        private final int MAX_SIZE;

        public SimpleCache(int size) {
            super(size, 0.75f, true);       // 접근 순서 모드 활성화
            this.MAX_SIZE = size;
        }

        @Override
        protected boolean removeEldestEntry(Entry<K, V> eldest) {
            return size() > MAX_SIZE;
        }
    }

    public static void main(String[] args) {
        // --- [실무 활용 1] 데이터 그룹화 및 집계 ---
        List<String> rawData = Arrays.asList("Apple", "Java", "Apple", "Python", "Java", "Java");

        // 멀티스레드 환경을 고려한 ConcurrentHashMap 사용
        Map<String, Integer> countMap = new ConcurrentHashMap<>();

        for (String item : rawData) {
            // getOrDefault: 값이 없으면 0을 반환하여 1을 더함
            countMap.put(item, countMap.getOrDefault(item, 0) + 1);
        }
        System.out.println("1. 데이터 집계 결과: " + countMap);

        // --- [실무 활용 2] 캐시 로직 통합 ---
        // 최대 3개까지만 보관하는 캐시 생성
        SimpleCache<String, String> sessionCache = new SimpleCache<>(3);

        System.out.println("\n2. 캐싱 동작 시작 (최대 크기: 3)");
        sessionCache.put("User_A", "SessionData_1");
        sessionCache.put("User_B", "SessionData_2");
        sessionCache.put("User_C", "SessionData_3");

        // User_A 데이터를 조회하여 "최근 사용됨" 상태로 만듦
        sessionCache.get("User_A");

        // 새로운 데이터 추가 (용량 초과로 인해 가장 오래된 User_B가 삭제됨)
        sessionCache.put("User_D", "SessionData_4");

        System.out.println("현재 캐시 상태: " + sessionCache);

        // --- [실무 활용 3] 복합 로직 (computeIfAbsent) ---
        Map<String, List<String>> categoryMap = new HashMap<>();

        // 데이터가 없으면 새 ArrayList를 만들고, 있으면 기존 리스트에 추가
        categoryMap.computeIfAbsent("언어", k -> new ArrayList<>()).add("Java");
        categoryMap.computeIfAbsent("과일", k -> new ArrayList<>()).add("Apple");

        System.out.println("\n3. 카테고리 분류 결과: " + categoryMap);
    }

}

















