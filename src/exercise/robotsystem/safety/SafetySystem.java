package exercise.robotsystem.safety;

import exercise.robotsystem.motor.Motor;
import exercise.robotsystem.sensor.CollisionSensor;

/**
 * 안전 시스템
 * - 충돌 감지 시 즉시 모터 정지
 */
public class SafetySystem {

    private final CollisionSensor sensor;

    public SafetySystem(CollisionSensor sensor) {
        this.sensor = sensor;
    }

    /**
     * 시스템 상태 체크
     */
    public void checkSafety(Motor motor) {
        if (sensor.isCollisionDetected()) {
            System.out.println("[Saftey] 충돌 감지! 즉시 정지");
            motor.stop();
            throw new IllegalArgumentException("충돌 발생");
        }
    }


}
