package exercise.hotbusinesslogic.service;

import exercise.hotbusinesslogic.entity.Payment;
import exercise.hotbusinesslogic.exception.BusinessException;
import exercise.hotbusinesslogic.status.PaymentStatus;
import java.util.UUID;

/**
 * 결제 서비스
 */
public class PaymentService {

    public Payment pay(int amount) {
        if (amount <= 0) {
            throw new BusinessException("결제 금액 오류");
        }

        // 외부 PG 연동 가정
        System.out.println("결제 진행: " + amount);

        return new Payment(UUID.randomUUID().toString(), amount, PaymentStatus.SUCCESS);
    }

}
