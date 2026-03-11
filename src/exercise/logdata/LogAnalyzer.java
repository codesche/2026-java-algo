package exercise.logdata;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Generic: 유저 ID 타입을 유연하게 변경 가능
 *  Stream 필터링: 실무에서 조건 검색할 때 많이 사용됨
 *  groupingBy: 실무 핵심 - SELECT userId, COUNT(*) GROUP BY userId
 *  msg.split(" ") -> 로그 분석, NLP, 텍스트 분석에 활용
 *  .flatMap(msg -> Arrays.stream(msg.split(" ")))
 *  ["LOGIN SUCCESS", "BUY ITEM"] -> ["LOGIN","SUCCESS","BUY","ITEM"]
 */

public class LogAnalyzer {

    public static void main(String[] args) {

        List<Log<String>> logs = List.of(
            new Log<>("user1", "LOGIN SUCCESS", 1),
            new Log<>("user2", "LOGIN FAIL", 3),
            new Log<>("user1", "BUY ITEM", 1),
            new Log<>("user3", "LOGIN SUCCESS", 1),
            new Log<>("user2", "LOGOUT", 1),
            new Log<>("user1", "LOGOUT", 1)
        );

        // 1. 특정 사용자 로그 필터링
        List<Log<String>> user1Logs = logs.stream()
            .filter(log -> log.getUserId().equals("user1"))
            .collect(Collectors.toList());

        System.out.println("=== user1 로그 ===");
        user1Logs.forEach(System.out::println);

        // 2. 사용자별 로그 개수 (자료구조 Map + Stream)
        Map<String, Long> countByUser = logs.stream()
            .collect(Collectors.groupingBy(
                Log::getUserId,
                Collectors.counting()
            ));

        System.out.println("\n=== 사용자별 로그 수 ===");
        countByUser.forEach((user, count) ->
            System.out.println(user + ": " + count));

        // 3. 로그 메시지 키워드 추출 (String + Stream)
        Set<String> keywords = logs.stream()
            .map(Log::getMessage)
            .flatMap(msg -> Arrays.stream(msg.split(" ")))
            .collect(Collectors.toSet());

        System.out.println("\n=== 키워드 목록 ===");
        keywords.forEach(System.out::println);

        // 4. 에러 로그 찾기 (level >= 3)
        List<Log<String>> errorLogs = logs.stream()
            .filter(log -> log.getLevel() >= 3)
            .collect(Collectors.toList());

        System.out.println("\n=== 에러 로그 ===");
        errorLogs.forEach(System.out::println);

        // 5. 가장 많은 로그를 남긴 사용자 찾기
        String topUser = countByUser.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("없음");

        System.out.println("\n가장 많은 로그 사용자 : " + topUser);
    }

}
