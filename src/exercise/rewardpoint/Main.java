package exercise.rewardpoint;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        User user = new User("user1", Grade.VIP, true);
        Order order = new Order(250000);

        PointService pointService = new PointService(
            List.of(
                new GradePointPolicy(),
                new FirstOrderPolicy(),
                new HighAmountPolicy()
            )
        );

        int point = pointService.calculatePoint(user, order);

        System.out.println("적립 포인트 = " + point);
    }

}
