package exercise.rewardpoint;

public class GradePointPolicy implements PointPolicy{

    @Override
    public int calculate(User user, Order order) {

        return switch (user.getGrade()) {
            case NORMAL -> (int) (order.getAmount() * 0.01);
            case VIP -> (int) (order.getAmount() * 0.03);
            case BLACK -> (int) (order.getAmount() * 0.05);
            default -> 0;
        };

    }
}
