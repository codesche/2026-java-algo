package theory.taxi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TaxiService {

    private final List<Driver> drivers = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong driverIdGen = new AtomicLong();
    private final AtomicLong reservationIdGen = new AtomicLong();

    public void registerDriver(String name, int location) {
        Driver driver = new Driver(driverIdGen.incrementAndGet(), name, location);
        drivers.add(driver);
        System.out.println("기사 등록 완료: " + driver);
    }

    public Reservation requestReservation(String passenger, int pickup, int destination) {
        Reservation reservation = new Reservation(
            reservationIdGen.incrementAndGet(),
            passenger,
            pickup,
            destination
        );
        reservations.add(reservation);
        assignDriver(reservation);
        return reservation;
    }

    private void assignDriver(Reservation reservation) {
        List<Driver> availableDrivers = drivers.stream()
            .filter(d -> d.getStatus() == TaxiStatus.AVAILABLE)
            .collect(Collectors.toList());

        if (availableDrivers.isEmpty()) {
            System.out.println("배차 가능한 기사 없음");
            return;
        }

        Driver closestDriver = availableDrivers.stream()
            .min(Comparator.comparingInt(
                d -> Math.abs(d.getLocation() - reservation.getPickupLocation())))
            .orElseThrow();

        closestDriver.assign();
        reservation.assignDriver(closestDriver);

        System.out.println("배차 완료 → 기사: " + closestDriver.getName());
    }

    public void completeRide(long reservationId) {
        Reservation reservation = reservations.stream()
            .filter(r -> r.getId() == reservationId)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("예약 없음"));

        if (reservation.getStatus() != ReservationStatus.ASSIGNED) {
            throw new IllegalStateException("운행 중이 아님");
        }

        Driver driver = reservation.getAssignedDriver();
        driver.complete(reservation.getDestination());
        reservation.complete();

        System.out.println("운행 완료: " + reservation);
    }

    public void printReservations() {
        reservations.forEach(System.out::println);
    }

}
