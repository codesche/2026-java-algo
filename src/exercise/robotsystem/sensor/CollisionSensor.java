package exercise.robotsystem.sensor;

import java.util.Random;

/**
 * 충돌 감지 센서
 */
public class CollisionSensor {

    private final Random random = new Random();

    /**
     * 충돌 여부 감지 (랜덤 시뮬레이션)
     */
    public boolean isCollisionDetected() {
        return random.nextInt(100) < 5;     // 5% 확률
    }

}
