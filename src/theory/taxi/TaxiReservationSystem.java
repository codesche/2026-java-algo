package theory.taxi;

public class TaxiReservationSystem {

    public static void main(String[] args) {
        TaxiService service = new TaxiService();

        // 기사 등록
        service.registerDriver("김기사", 10);
        service.registerDriver("이기사", 50);
        service.registerDriver("박기사", 30);

        // 예약 요청
        Reservation r1 = service.requestReservation("홍길동", 20, 80);
        Reservation r2 = service.requestReservation("김철수", 40, 60);

        // 현재 예약 상태 출력
        service.printReservations();

        // 운행 완료
        service.completeRide(r1.getId());

        System.out.println("\n=== 최종 상태 ===");
        service.printReservations();
    }

}
