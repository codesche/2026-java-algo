package exercise.hspt.domain;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private final String id;
    private final Patient patient;
    private final Doctor doctor;
    private String diagnosis;
    private final List<Prescription> prescriptions = new ArrayList<>();

    public MedicalRecord(String id, Patient patient, Doctor doctor, String diagnosis) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
    }

    public void addPrescription(Prescription p) {
        prescriptions.add(p);
    }

    public String getId() {
        return id;
    }

}
