package exercise.robotsystem.motor;

/**
 * 모터의 기본 동작을 정의하는 인터페이스
 */
public interface Motor {

    /**
     * 지정된 속도로 모터를 회전시킨다,
     * @param speed 속도 (0 ~ 100)
     */
    void rotate(int speed);

    /**
     * 모터를 정지시킨다
     */
    void stop();

    /**
     * 현재 모터 상태 반환
     */
    boolean isRunning();

}
