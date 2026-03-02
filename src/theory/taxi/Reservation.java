package theory.taxi;

public class Reservation {

    private final long id;
    private final String passengerName;
    private final int pickupLocation;
    private final int destination;
    private ReservationStatus status;
    private Driver assignedDriver;

    public Reservation(long id, String passengerName, int pickupLocation, int destination) {
        this.id = id;
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.status = ReservationStatus.REQUESTED;
    }

    public void assignDriver(Driver driver) {
        this.assignedDriver = driver;
        this.status = ReservationStatus.ASSIGNED;
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
    }

    public long getId() {
        return id;
    }

    public int getPickupLocation() {
        return pickupLocation;
    }

    public int getDestination() {
        return destination;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + id +
            ", passenger='" + passengerName + '\'' +
            ", pickup=" + pickupLocation +
            ", destination=" + destination +
            ", status=" + status +
            ", driver=" + (assignedDriver != null ? assignedDriver.getName() : "None") +
            '}';
    }
}
