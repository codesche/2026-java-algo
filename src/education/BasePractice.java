package education;

import java.util.Scanner;

/**
 * Java 기초 종합 실습
 * - 변수 / 자료형
 * - 조건문
 * - 반복문
 * - 배열
 * - 메서드
 * - 객체지향 (클래스)
 */

public class BasePractice {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 변수와 자료형
        System.out.print("이름을 입력하세요: ");
        String name = scanner.nextLine();

        System.out.print("나이를 입력하세요: ");
        int age = scanner.nextInt();

        // 2. 조건문
        if (age >= 20) {
            System.out.println(name + "님은 성인입니다.");
        } else {
            System.out.println(name + "님은 미성년자입니다.");
        }

        // 3. 배열 + 반복문
        int[] scores = new int[3];
        System.out.println("점수 3개를 입력하세요.");

        for (int i = 0; i < scores.length; i++) {
            scores[i] = scanner.nextInt();
        }

        int sum = calculateSum(scores);
        double avg = (double) sum / scores.length;

        System.out.println("총점: " + sum);
        System.out.println("평균: " + avg);

        // 4. 객체 생성
        Student student = new Student(name, age, avg);
        student.printInfo();

        scanner.close();
    }

    /**
     * 배열의 총합을 구하는 메서드
     */
    public static int calculateSum(int[] arr) {
        int sum = 0;

        for (int num : arr) {
            sum += num;
        }

        return sum;
    }
}

/**
 * 학생 클래스 (객체지향 기초)
 */
class Student {
    private String name;
    private int age;
    private double average;

    public Student(String name, int age, double average) {
        this.name = name;
        this.age = age;
        this.average = average;
    }

    public void printInfo() {
        System.out.println("=== 학생 정보 ===");
        System.out.println("이름: " + name);
        System.out.println("나이: " + age);
        System.out.println("평균 점수: " + average);
    }
}
