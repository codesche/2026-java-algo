package exercise.rewardpoint;

/**
 * 모든 정책은 PointPolicy를 구현
 */
public interface PointPolicy {

    int calculate(User user, Order order);

}
