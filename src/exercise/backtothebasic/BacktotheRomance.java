package exercise.backtothebasic;

import java.util.ArrayList;
import java.util.List;

// ──────────────────────────────────────────────
// 1. INTERFACE — 공통 행동 계약 정의
// ──────────────────────────────────────────────
interface Evaluatable {
    double calculateBonus();
    String getPerformanceGrade();
}

interface Printable {
    void printInfo();
}

// ──────────────────────────────────────────────
// 2. ABSTRACT CLASS — 공통 속성 + 공통 로직 보유
// ──────────────────────────────────────────────

abstract class Employee implements Evaluatable, Printable {

    private final String id;
    private final String name;
    protected double baseSalary;

    public Employee(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    // 공통 구현 - 하위 클래스에서 재사용
    @Override
    public void printInfo() {
        System.out.printf("[%s] %s | 기본급: %.0f원 | 보너스: %.0f원 | 등급: %s%n",
            id, name, baseSalary, calculateBonus(), getPerformanceGrade());
    }

    // 추상 메서드 - 하위 클래스가 반드시 구현
    public abstract String getDepartment();
}

// ──────────────────────────────────────────────
// 3. CONCRETE CLASS — 상속 + 인터페이스 구현
// ──────────────────────────────────────────────

class FullTimeEmployee extends Employee {

    private final double performanceScore;      // 0.0 ~ 1.0

    public FullTimeEmployee(String id, String name, double baseSalary, double performanceScore) {
        super(id, name, baseSalary);
        this.performanceScore = performanceScore;
    }

    @Override
    public double calculateBonus() {
        // 실무 패턴: 점수 구간별 보너스 비율
        if (performanceScore >= 0.9) return baseSalary * 0.30;
        if (performanceScore >= 0.7) return baseSalary * 0.15;
        if (performanceScore >= 0.5) return baseSalary * 0.05;
        return 0;
    }

    @Override
    public String getPerformanceGrade() {
        if (performanceScore >= 0.9) return "S";
        if (performanceScore >= 0.7) return "A";
        if (performanceScore >= 0.5) return "B";
        return "C";
    }

    @Override
    public String getDepartment() {
        return "정규직";
    }
}

class ContractEmployee extends Employee {

    private final int workedMonths;
    private static final int CONTRACT_PERIOD = 12;

    public ContractEmployee(String id, String name, double baseSalary, int workedMonths) {
        super(id, name, baseSalary);
        this.workedMonths = workedMonths;
    }

    @Override
    public double calculateBonus() {
        // 계약직: 근무 기간 비례 보너스
        double ratio = (double) workedMonths / CONTRACT_PERIOD;
        return baseSalary * 0.10 * Math.min(ratio, 1.0);
    }

    @Override
    public String getPerformanceGrade() {
        return workedMonths >= CONTRACT_PERIOD ? "A" : "B";
    }

    @Override
    public String getDepartment() { return "계약직"; }

}

class InternEmployee extends Employee {

    private final String mentor;

    public InternEmployee(String id, String name, double baseSalary, String mentor) {
        super(id, name, baseSalary);
        this.mentor = mentor;
    }

    @Override
    public double calculateBonus() {
        return 0; // 인턴은 보너스 없음
    }

    @Override
    public String getPerformanceGrade() { return "N/A"; }

    @Override
    public String getDepartment() { return "인턴"; }

    @Override
    public void printInfo() {
        // 오버라이드: 인턴은 멘토 정보 추가 출력
        super.printInfo();
        System.out.printf("    └─ 담당 멘토: %s%n", mentor);
    }
}

// ──────────────────────────────────────────────
// 4. SERVICE CLASS — 비즈니스 로직 집합
// ──────────────────────────────────────────────

class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void printAll() {
        System.out.println("=== 전체 직원 현황 ===");
        employees.forEach(Employee::printInfo);
    }

    public double getTotalBonusBudget() {
        return employees.stream()
            .mapToDouble(Employee::calculateBonus)
            .sum();
    }

    public List<Employee> getByGrade(String grade) {
        return employees.stream()
            .filter(e -> e.getPerformanceGrade().equals(grade))
            .toList();
    }
}


public class BacktotheRomance {

    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();

        service.addEmployee(new FullTimeEmployee("E001", "김지수", 4_000_000, 0.95));
        service.addEmployee(new FullTimeEmployee("E002", "박민준", 3_500_000, 0.72));
        service.addEmployee(new FullTimeEmployee("E003", "이서연", 3_200_000, 0.45));
        service.addEmployee(new ContractEmployee("C001", "최현우", 2_800_000, 10));
        service.addEmployee(new ContractEmployee("C002", "정다은", 2_600_000, 12));
        service.addEmployee(new InternEmployee("I001", "한도영", 2_000_000, "김지수"));

        service.printAll();

        System.out.printf("%n=== 총 보너스 예산: %.0f원 ===%n", service.getTotalBonusBudget());

        System.out.println("\n=== S등급 직원 ===");
        service.getByGrade("S").forEach(e ->
            System.out.printf("  → %s (%s)%n", e.getName(), e.getDepartment()));

    }

}
