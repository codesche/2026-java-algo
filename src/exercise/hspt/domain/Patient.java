package exercise.hspt.domain;

/**
 * 병원 진료 예약 시스템
 * - 예약 (Create / Read / Update / Delete)
 * - 진료 기록 관리
 * - 처방 관리
 * - Repository / Service / Controller 구조
 */
public class Patient {
    private final String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
