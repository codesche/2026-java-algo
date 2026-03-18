package exercise.robotsystem.motor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DC 모터 구현체
 */
public class DcMotor implements Motor {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private int speed;

    @Override
    public void rotate(int speed) {
        if (speed < 0 || speed > 100) {
            throw new IllegalArgumentException("속도는 0~100 사이여야 합니다.");
        }

        this.speed = speed;
        running.set(true);

        System.out.println("[Motor] 속도 " + speed + "으로 회전 시작");
    }

    @Override
    public void stop() {
        running.set(false);
        speed = 0;
        System.out.println("[Motor] 정지");
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }
}
