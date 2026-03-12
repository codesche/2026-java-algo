package exercise.datastructure;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataStructureEx {

    // Generic 유틸 메서드
    public static <T> void printTop(List<T> list, int limit) {
        list.stream()
            .limit(limit)
            .forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<User> users = List.of(
            new User("Alice", 80),
            new User("Bob", 95),
            new User("Charlie", 70),
            new User("David", 95),
            new User("Eve", 60)
        );

        // 1. 점수 기준 정렬
        List<User> sorted = users.stream()
            .sorted(Comparator.comparing(User::getScore).reversed())
            .collect(Collectors.toList());

        System.out.println("=== Top Users ===");
        printTop(sorted, 3);

        // 2. 점수별 그룹화
        Map<Integer, List<User>> grouped =
            users.stream().collect(Collectors.groupingBy(User::getScore));

        System.out.println("\n=== Group By Score");
        grouped.forEach((score, list) ->
            System.out.println(score + " : " + list));
    }

}
