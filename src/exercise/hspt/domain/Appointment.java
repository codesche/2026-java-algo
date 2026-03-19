package exercise.hspt.domain;

import java.util.Date;

public class Appointment {

    private final String id;
    private final Patient patient;
    private final Doctor doctor;
    private Date date;

    public Appointment(String id, Patient patient, Doctor doctor, Date date) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getDate() {
        return date;
    }

    public void updateDate(Date newDate) {
        this.date = newDate;
    }

}
