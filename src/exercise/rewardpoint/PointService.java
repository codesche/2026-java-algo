package exercise.rewardpoint;

import java.util.List;

/**
 * 새로운 정책이 추가될 때마다 클래스를 하나 추가하는 형태로
 * 유지보수를 할 수 있다 -> 비용 절감
 */
public class PointService {

    private List<PointPolicy> policies;

    public PointService(List<PointPolicy> policies) {
        this.policies = policies;
    }

    public int calculatePoint(User user, Order order) {
        return policies.stream()
            .mapToInt(policy -> policy.calculate(user, order))
            .sum();
    }

}
