package exercise.hspt;

import exercise.hspt.controller.HospitalController;
import exercise.hspt.domain.Appointment;
import exercise.hspt.domain.Doctor;
import exercise.hspt.domain.MedicalRecord;
import exercise.hspt.domain.Patient;
import exercise.hspt.domain.Prescription;
import exercise.hspt.repository.BaseRepository;
import exercise.hspt.repository.InMemoryRepository;
import exercise.hspt.service.AppointmentService;
import exercise.hspt.service.MedicalRecordService;
import java.util.Date;

/**
 * 실행
 */
public class Main {

    public static void main(String[] args) {

        // Repository 생성
        BaseRepository<Appointment> appointmentRepo = new InMemoryRepository<>(Appointment::getId);
        BaseRepository<MedicalRecord> recordRepo = new InMemoryRepository<>(MedicalRecord::getId);

        // Service 생성
        AppointmentService appointmentService = new AppointmentService(appointmentRepo);
        MedicalRecordService recordService = new MedicalRecordService(recordRepo);

        // Controller 생성
        HospitalController controller = new HospitalController(appointmentService, recordService);

        // Domain 객체 생성
        Patient patient = new Patient("P1", "홍길동");
        Doctor doctor = new Doctor("D1", "김의사");

        // 예약
        Appointment appt = new Appointment("A1", patient, doctor, new Date());

        // 진료 기록 생성
        MedicalRecord record = new MedicalRecord("R1", patient, doctor, "감기");
        controller.addRecord(record);

        // 처방 추가
        controller.prescribe("R1", new Prescription("PR1", "타이레놀"));

        // 조회
        controller.printAllAppointments();
    }

}
