package exercise.backtothebasic;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

// ──────────────────────────────────────────────
// 1. 인터페이스 정의
// ──────────────────────────────────────────────
interface Reservable {
    boolean reserve(Patient patient, LocalDateTime dateTime);
    boolean cancel(String reservationId);
    List<Reservation> getReservations();
}

interface Notifiable {
    void notifyReservation(Patient patient, Reservation reservation);
    void notifyCancel(Patient patient, String reservationId);
}

interface Reportable {
    void printSummary();
}

// ──────────────────────────────────────────────
// 2. 공통 도메인 클래스
// ──────────────────────────────────────────────

class Patient {
    private final String id;
    private final String name;
    private final int age;
    private final String phone;

    public Patient(String id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getId()    { return id; }
    public String getName()  { return name; }
    public int getAge()      { return age; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("Patient[%s | %s | %d세 | %s]", id, name, age, phone);
    }
}

class Reservation {
    private static int counter = 1;

    private final String reservationId;
    private final String patientId;
    private final String department;
    private final LocalDateTime dateTime;
    private boolean cancelled;

    public Reservation(String patientId, String department, LocalDateTime dateTime) {
        this.reservationId = "RES-" + String.format("%04d", counter++);
        this.patientId = patientId;
        this.department = department;
        this.dateTime = dateTime;
        this.cancelled = false;
    }

    public void cancel()              { this.cancelled = true; }
    public String getReservationId()  { return reservationId; }
    public String getPatientId()      { return patientId; }
    public String getDepartment()     { return department; }
    public LocalDateTime getDateTime(){ return dateTime; }
    public boolean isCancelled()      { return cancelled; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("[%s] %s | %s | %s",
            reservationId, department, dateTime.format(fmt),
            cancelled ? "취소됨" : "예약 중");
    }
}

// ──────────────────────────────────────────────
// 3. 부서별 구현체 (인터페이스 다형성)
// ──────────────────────────────────────────────

class InternalMedicineDept implements Reservable, Notifiable, Reportable {

    private final String deptName = "내과";
    private final List<Reservation> reservations = new ArrayList<>();
    private final int maxPerSlot = 5;

    @Override
    public void notifyReservation(Patient patient, Reservation reservation) {
        System.out.printf("  [SMS → %s] %s 예약 완료: %s%n",
            patient.getPhone(), deptName, reservation.getReservationId());
    }

    @Override
    public void notifyCancel(Patient patient, String reservationId) {
        System.out.printf("  [SMS → %s] %s 예약 취소: %s%n",
            patient.getPhone(), deptName, reservationId);
    }

    @Override
    public void printSummary() {
        long active = reservations.stream().filter(r -> !r.isCancelled()).count();
        System.out.printf("[%s] 전체 예약 %d건 / 활성 %d건%n",
            deptName, reservations.size(), active);
    }

    @Override
    public boolean reserve(Patient patient, LocalDateTime dateTime) {
        long count = reservations.stream()
            .filter(r -> r.getDateTime().equals(dateTime) && !r.isCancelled())
            .count();
        if (count >= maxPerSlot) {
            System.out.println("[" + deptName + "] 해당 시간대 예약이 꽉 찼습니다: " + dateTime);
            return false;
        }
        Reservation res = new Reservation(patient.getId(), deptName, dateTime);
        reservations.add(res);
        notifyReservation(patient, res);
        return true;
    }

    @Override
    public boolean cancel(String reservationId) {
        return reservations.stream()
            .filter(r -> r.getReservationId().equals(reservationId) && !r.isCancelled())
            .findFirst()
            .map(r -> { r.cancel(); return true; })
            .orElse(false);
    }

    @Override
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}

class OrthopedicsDept implements Reservable, Notifiable, Reportable {

    private final String deptName = "정형외과";
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public void notifyReservation(Patient patient, Reservation reservation) {
        System.out.printf("  [앱 알림 → %s] %s 예약 완료: %s%n",
            patient.getName(), deptName, reservation.getReservationId());
    }

    @Override
    public void notifyCancel(Patient patient, String reservationId) {
        System.out.printf("  [앱 알림 → %s] %s 예약 취소: %s%n",
            patient.getName(), deptName, reservationId);
    }

    @Override
    public void printSummary() {
        long active = reservations.stream().filter(r -> !r.isCancelled()).count();
        System.out.printf("[%s] 전체 예약 %d건 / 활성 %d건%n",
            deptName, reservations.size(), active);
    }

    @Override
    public boolean reserve(Patient patient, LocalDateTime dateTime) {
        // 정형외과: 60세 이상 우선 예약 가능
        if (patient.getAge() < 60 && hasElderlyWaitlist(dateTime)) {
            System.out.println("[" + deptName + "] 해당 시간대는 고령 환자 우선입니다.");
            return false;
        }
        Reservation res = new Reservation(patient.getId(), deptName, dateTime);
        reservations.add(res);
        notifyReservation(patient, res);
        return true;
    }

    private boolean hasElderlyWaitlist(LocalDateTime dateTime) {
        // 간단히 특정 시간대는 시니어 전용으로 설정 (예시)
        return dateTime.getHour() == 9;
    }

    @Override
    public boolean cancel(String reservationId) {
        return reservations.stream()
            .filter(r -> r.getReservationId().equals(reservationId) && !r.isCancelled())
            .findFirst()
            .map(r -> { r.cancel(); return true; })
            .orElse(false);
    }

    @Override
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}

class HospitalManager {
    // Reservable 인터페이스 타입으로 저장 → 다형성 핵심
    private final Map<String, Reservable> departments = new LinkedHashMap<>();
    private final Map<String, Patient> patients = new HashMap<>();

    public void registerDepartment(String key, Reservable dept) {
        departments.put(key, dept);
        System.out.println("부서 등록 완료: " + key);
    }

    public void registerPatient(Patient patient) {
        patients.put(patient.getId(), patient);
        System.out.println(" 환자 등록: " + patient);
    }

    public boolean makeReservation(String patientId, String deptKey, LocalDateTime dateTime) {
        Patient patient = patients.get(patientId);
        Reservable dept = departments.get(deptKey);

        if (patient == null) { System.out.println("  존재하지 않는 환자: " + patientId); return false; }
        if (dept == null)    { System.out.println("  존재하지 않는 부서: " + deptKey);   return false; }

        return dept.reserve(patient, dateTime);
    }

    public boolean cancelReservation(String patientId, String deptKey, String reservationId) {
        Patient patient = patients.get(patientId);
        Reservable dept = departments.get(deptKey);

        if (patient == null || dept == null) return false;

        boolean result = dept.cancel(reservationId);
        if (result && dept instanceof Notifiable notifiable) {
            notifiable.notifyCancel(patient, reservationId);
        }
        return result;
    }

    public void printAllReservations() {
        System.out.println("\n=== 전체 예약 현황 ===");
        departments.forEach((key, dept) -> {
            System.out.println("\n▶ " + key);
            dept.getReservations().forEach(r -> System.out.println("  " + r));
        });
    }

    public void printAllSummaries() {
        System.out.println("\n=== 부서별 통계 ===");
        departments.values().forEach(dept -> {
            if (dept instanceof Reportable reportable) {
                reportable.printSummary();
            }
        });
    }

}


public class ObjectiveProgrammingEx {

    public static void main(String[] args) {

        System.out.println("=== 병원 예약 시스템 시작 ===\n");

        HospitalManager manager = new HospitalManager();

        // 부서 등록
        System.out.println("── 부서 등록 ──");
        manager.registerDepartment("내과", new InternalMedicineDept());
        manager.registerDepartment("정형외과", new OrthopedicsDept());

        // 환자 등록
        System.out.println("\n── 환자 등록 ──");
        manager.registerPatient(new Patient("P001", "김민준", 35, "010-1111-2222"));
        manager.registerPatient(new Patient("P002", "이서연", 62, "010-3333-4444"));
        manager.registerPatient(new Patient("P003", "박지호", 28, "010-5555-6666"));

        // 예약 시도
        System.out.println("\n── 예약 처리 ──");
        LocalDateTime slot1 = LocalDateTime.of(2025, 7, 10, 10, 0);
        LocalDateTime slot2 = LocalDateTime.of(2025, 7, 10, 9, 0);  // 정형외과 시니어 우선 슬롯
        LocalDateTime slot3 = LocalDateTime.of(2025, 7, 11, 14, 0);

        manager.makeReservation("P001", "내과", slot1);
        manager.makeReservation("P002", "내과", slot1);
        manager.makeReservation("P003", "정형외과", slot2);   // 28세 → 9시 슬롯 거절
        manager.makeReservation("P002", "정형외과", slot2);   // 62세 → 9시 슬롯 허용
        manager.makeReservation("P001", "정형외과", slot3);

        // 전체 예약 조회
        manager.printAllReservations();

        // 취소
        System.out.println("\n── 예약 취소 ──");
        manager.cancelReservation("P001", "내과", "RES-0001");

        // 통계
        manager.printAllSummaries();

        System.out.println("\n=== 시스템 종료 ===");

    }
}
