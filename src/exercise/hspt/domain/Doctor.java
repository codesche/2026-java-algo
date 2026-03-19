package exercise.hspt.domain;

public class Doctor {

    private final String id;
    private final String name;

    public Doctor(String id, String name) {
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
