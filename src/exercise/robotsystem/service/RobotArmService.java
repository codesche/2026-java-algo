package exercise.robotsystem.service;

import exercise.robotsystem.motor.Motor;
import exercise.robotsystem.safety.SafetySystem;

/**
 * 로봇 팔 제어 서비스
 */
public class RobotArmService {

    private final Motor motor;
    private final SafetySystem safetySystem;

    public RobotArmService(Motor motor, SafetySystem safetySystem) {
        this.motor = motor;
        this.safetySystem = safetySystem;
    }

    /**
     * 로봇 팔 이동
     */
    public void moveArm(int speed, int durationMs) {
        try {
            motor.rotate(speed);

            long start = System.currentTimeMillis();

            while (System.currentTimeMillis() - start < durationMs) {
                safetySystem.checkSafety(motor);

                Thread.sleep(100);      // CPU 과부하 방지
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        } finally {
            motor.stop();
        }
    }

}
