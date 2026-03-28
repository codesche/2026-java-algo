package exercise.hotbusinesslogic.exception;

/**
 * 비즈니스 예외 로직 처리
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
