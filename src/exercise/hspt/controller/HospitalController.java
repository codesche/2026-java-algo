package exercise.hspt.controller;

import exercise.hspt.domain.Appointment;
import exercise.hspt.domain.MedicalRecord;
import exercise.hspt.domain.Prescription;
import exercise.hspt.service.AppointmentService;
import exercise.hspt.service.MedicalRecordService;

public class HospitalController {

    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;

    public HospitalController(AppointmentService appointmentService,
        MedicalRecordService medicalRecordService) {
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
    }

    public void reserve(Appointment appt) {
        appointmentService.reserve(appt);
    }

    public void addRecord(MedicalRecord record) {
        medicalRecordService.save(record);
    }

    public void prescribe(String recordId, Prescription p) {
        medicalRecordService.addPrescription(recordId, p);
    }

    public void printAllAppointments() {
        appointmentService.getAll().forEach(a ->
            System.out.println("예약: " + a.getId() + ", 환자: " + a.getPatient().getName())
        );
    }

}
