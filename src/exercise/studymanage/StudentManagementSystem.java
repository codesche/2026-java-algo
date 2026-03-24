package exercise.studymanage;

/**
 * 학생 성적 관리 시스템 (간단 버전)
 * 1. 학생 리스트 생성
 * 2. 평균 점수 계산
 * 3. 특정 점수 이상 학생 필터링
 * 4. 이름 기준 정렬
 * 5. 결과 출력
 */

import java.util.*;
import java.util.stream.Collectors;

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + " (" + score + ")";
    }
}

public class StudentManagementSystem {

    public static void main(String[] args) {

        // 1. 학생 데이터 생성
        List<Student> students = Arrays.asList(
            new Student("Alice", 85),
            new Student("Bob", 70),
            new Student("Charlie", 90),
            new Student("David", 60),
            new Student("Eve", 95)
        );

        // 2. 평균 점수 계산
        double average = students.stream()
            .mapToInt(Student::getScore)
            .average()
            .orElse(0);

        System.out.println("평균 점수: " + average);

        // 3. 80점 이상 학생 필터링
        List<Student> highScoreStudents = students.stream()
            .filter(s -> s.getScore() >= 80)
            .collect(Collectors.toList());

        System.out.println("\n80점 이상 학생: ");
        highScoreStudents.forEach(System.out::println);

        // 4. 이름 기준 정렬
        List<Student> sortedStudents = students.stream()
            .sorted(Comparator.comparing(Student::getName))
            .collect(Collectors.toList());

        System.out.println("\n이름 순 정렬: ");
        sortedStudents.forEach(System.out::println);
    }

}
