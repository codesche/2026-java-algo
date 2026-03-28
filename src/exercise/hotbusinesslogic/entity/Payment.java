package exercise.hotbusinesslogic.entity;

import exercise.hotbusinesslogic.status.PaymentStatus;

/**
 * 결제
 */
public class Payment {

    private final String paymentId;
    private final int amount;
    private final PaymentStatus status;

    public Payment(String paymentId, int amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
    }

}
