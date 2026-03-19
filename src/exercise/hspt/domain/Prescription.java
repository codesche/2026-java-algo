package exercise.hspt.domain;

public class Prescription {

    private final String id;
    private final String medicine;

    public Prescription(String id, String medicine) {
        this.id = id;
        this.medicine = medicine;
    }

    public String getMedicine() {
        return medicine;
    }

}
