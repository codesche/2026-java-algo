package exercise.rewardpoint;

/**
 * 첫 구매 이벤트
 */
public class FirstOrderPolicy implements PointPolicy {

    @Override
    public int calculate(User user, Order order) {

        if (user.isFirstOrder()) {
            return 1000;
        }

        return 0;
    }
}
