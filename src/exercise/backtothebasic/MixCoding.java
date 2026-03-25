package exercise.backtothebasic;

/**
 * [람다식 + Stream 실습]
 * 직원 연봉 관리
 */

import java.util.*;
import java.util.stream.Collectors;

public class MixCoding {

    static class Employee {
        String name;
        int salary;

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return name + " (" + salary + ")";
        }
    }

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
            new Employee("김개발", 5000),
            new Employee("이백엔드", 7000),
            new Employee("박프론트", 6500),
            new Employee("최데이터", 8000),
            new Employee("정신입", 3000)
        );

        // 1. 전체 출력 (람다식)
        System.out.println("[전체 직원]");
        employees.forEach(e -> System.out.println(e));

        // 2. 연봉 6000 이상 직원 필터링
        System.out.println("\n[고연봉자 (6000 이상)]");
        List<Employee> highSalary = employees.stream()
            .filter(e -> e.salary >= 6000)
            .collect(Collectors.toList());

        highSalary.forEach(e -> System.out.println(e));

        // 3. 이름만 추출 (map)
        System.out.println("\n[직원 이름 목록]");
        List<String> names = employees.stream()
            .map(e -> e.name)
            .collect(Collectors.toList());

        names.forEach(name -> System.out.println(name));

        // 4. 연봉 기준 내림차순 정렬
        System.out.println("\n[연봉 내림차순 정렬]");
        List<Employee> sorted = employees.stream()
            .sorted((e1, e2) -> e2.salary - e1.salary)
            .collect(Collectors.toList());

        sorted.forEach(e -> System.out.println(e));

        // 5. 평균 연봉 계산
        double avgSalary = employees.stream()
            .mapToInt(e -> e.salary)
            .average()
            .orElse(0);

        System.out.println("\n평균 연봉: " + avgSalary);

        // 6. 최고 연봉자 찾기
        Employee top = employees.stream()
            .max((e1, e2) -> e1.salary - e2.salary)
            .orElse(null);

        System.out.println("\n최고 연봉자: " + top);

        // 7. 연봉 인상 (10% 증가)
        System.out.println("\n[연봉 10% 인상]");
        List<Employee> raised = employees.stream()
            .map(e -> new Employee(e.name, (int)(e.salary * 1.1)))
            .collect(Collectors.toList());

        raised.forEach(e -> System.out.println(e));
    }

}
