package theory.taxi;

public class Driver {

    private final long id;
    private final String name;
    private int location;       // 단순 거리 값
    private TaxiStatus status;

    public Driver(long id, String name, int location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = TaxiStatus.AVAILABLE;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    public TaxiStatus getStatus() {
        return status;
    }

    public void assign() {
        this.status = TaxiStatus.BUSY;
    }

    public void complete(int newLocation) {
        this.status = TaxiStatus.AVAILABLE;
        this.location = newLocation;
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", location=" + location +
            ", status=" + status +
            '}';
    }
}
