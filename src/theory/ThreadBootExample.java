package theory;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadBootExample {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== 1. 동기화 없이 실행 =====");

        UnsafeAccount unsafeAccount = new UnsafeAccount(1000);

        Thread[] threads1 = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    unsafeAccount.withdraw(10);
                }
            });
            threads1[i].start();
        }

        for (Thread t : threads1) {
            t.join();
        }

        System.out.println("최종 잔액 (동기화 없음): " + unsafeAccount.getBalance());
        System.out.println();

        // =====================================================

        System.out.println("===== 2. synchronized 적용 =====");

        SafeAccount safeAccount = new SafeAccount(2000);

        Thread[] threads2 = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads2[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    safeAccount.withdraw(10);
                }
            });
            threads2[i].start();
        }

        for (Thread t : threads2) {
            t.join();
        }

        System.out.println("최종 잔액 (synchronized 적용): " + safeAccount.getBalance());
        System.out.println();

        // =====================================================

        System.out.println("===== 3. AtomicInteger 사용 =====");

        AtomicAccount atomicAccount = new AtomicAccount(2000);

        Thread[] threads3 = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads3[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    atomicAccount.withdraw(10);
                }
            });
            threads3[i].start();
        }

        for (Thread t : threads3) {
            t.join();
        }

        System.out.println("최종 잔액 (AtomicInteger): " + atomicAccount.getBalance());
    }

    // ================= 동기화 안 한 계좌 =================
    static class UnsafeAccount {

        private int balance;

        public UnsafeAccount(int balance) {
            this.balance = balance;
        }

        public void withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(1);        // 의도적으로 충돌 유도
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
            }
        }

        public int getBalance() {
            return balance;
        }
    }

    // ================= synchronized 계좌 =================
    static class SafeAccount {

        private int balance;

        public SafeAccount(int balance) {
            this.balance = balance;
        }

        public synchronized void withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
            }
        }

        public int getBalance() {
            return balance;
        }
    }

    // ================= AtomicInteger 계좌 =================
    static class AtomicAccount {

        private AtomicInteger balance;

        public AtomicAccount(int balance) {
            this.balance = new AtomicInteger(balance);
        }

        public void withdraw(int amount) {
            while (true) {
                int current = balance.get();
                if (current < amount) break;
                if (balance.compareAndSet(current, current - amount)) {
                    break;
                }
            }
        }

        public int getBalance() {
            return balance.get();
        }
    }
}
