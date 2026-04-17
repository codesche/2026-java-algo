package datatype;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaWithStreamEx1 {

    // 테스트용 데이터 클래스
    static class User {
        private int id;
        private String name;
        private String department;
        private int score;

        public User(int id, String name, String department, int score) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.score = score;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public int getScore() { return score; }
    }

    public static void main(String[] args) {
        List<User> userList = Arrays.asList(
            new User(1, "Alice", "Sales", 85),
            new User(2, "Bob", "IT", 90),
            new User(3, "Charlie", "IT", 95),
            new User(4, "David", "Sales", 70)
        );

        // 1. Map 변환 (ID:User)
        Map<Integer, User> userMap = userList.stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
        System.out.println("User Map: " + userMap.keySet());

        // 2. 그룹화 (부서별)
        Map<String, List<User>> usersByDept = userList.stream()
            .collect(Collectors.groupingBy(User::getDepartment));
        System.out.println("IT Dept Members: " + usersByDept.get("IT").size());

        // 3. 필터링 및 이름 추출
        List<String> itNames = userList.stream()
            .filter(u -> "IT".equals(u.getDepartment()))
            .map(User::getName)
            .collect(Collectors.toList());
        System.out.println("IT Staff: " + itNames);

        // 4. 합계 및 통계
        int totalScore = userList.stream()
            .mapToInt(User::getScore)
            .sum();
        System.out.println("Total Score: " + totalScore);

        // 5. 문자열 연결
        String allNames = userList.stream()
            .map(User::getName)
            .collect(Collectors.joining(", "));
        System.out.println("All Users: " + allNames);
    }

}
