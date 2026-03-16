package exercise.rewardpoint;

/**
 * 고액 주문 이벤트
 */
public class HighAmountPolicy implements PointPolicy{

    @Override
    public int calculate(User user, Order order) {

        if (order.getAmount() > 200000) {
            return 500;
        }

        return 0;
    }
}
