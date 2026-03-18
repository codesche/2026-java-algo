package exercise.robotsystem;

import exercise.robotsystem.motor.DcMotor;
import exercise.robotsystem.safety.SafetySystem;
import exercise.robotsystem.sensor.CollisionSensor;
import exercise.robotsystem.service.RobotArmService;

/**
 * 로봇 팔 시뮬레이션 실행
 * 1. 유지보수
 * - Motor 교체 가능 (Servo Motor 추가 가능)
 * - Sensor 확장 가능
 *
 * 2. 안정성
 * - 충돌 감지 -> 즉시 stop
 * - 예외 처리 구조
 *
 * 3. 성능
 * - Thread Sleep -> CPU 낭비 방지
 * - AtomicBoolean -> thread-safe

 */
public class Main {

    public static void main(String[] args) {

        DcMotor motor = new DcMotor();
        CollisionSensor sensor = new CollisionSensor();
        SafetySystem safetySystem = new SafetySystem(sensor);

        RobotArmService robotArm = new RobotArmService(motor, safetySystem);

        System.out.println("=== 로봇 팔 동작 시작 ===");

        robotArm.moveArm(70, 3000);

        System.out.println("=== 작업 종료 ===");
    }


}
