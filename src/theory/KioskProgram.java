package theory;

import java.util.*;

/**
 * 1. Map 활용
 * 2. getOrDefault 사용
 * 3. 예외 처리 (InputMismatchException)
 * 4. 재고 관리 로직
 * 5. 비즈니스 로직 흐름 설계
 * 6. 반복문 기반 상태 제어
 */

public class KioskProgram {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 메뉴 구성 (상품명, 가격, 재고)
        Map<Integer, String> menu = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        Map<String, Integer> stockMap = new HashMap<>();

        menu.put(1, "아메리카노");
        menu.put(2, "라떼");
        menu.put(3, "카푸치노");
        menu.put(4, "샌드위치");

        priceMap.put("아메리카노", 3000);
        priceMap.put("라떼", 3500);
        priceMap.put("카푸치노", 4000);
        priceMap.put("샌드위치", 5000);

        stockMap.put("아메리카노", 10);
        stockMap.put("라떼", 10);
        stockMap.put("카푸치노", 10);
        stockMap.put("샌드위치", 5);

        // 장바구니 (상품명, 수량)
        Map<String, Integer> cart = new HashMap<>();

        boolean running = true;

        while (running) {

            System.out.println("\n===== 🏪 카페 키오스크 =====");
            for (int key : menu.keySet()) {
                String item = menu.get(key);
                System.out.printf("%d. %s (%d원) [재고:%d]\n",
                    key, item, priceMap.get(item), stockMap.get(item));
            }
            System.out.println("5. 결제하기");
            System.out.println("0. 종료");
            System.out.print("선택 > ");

            try {
                int choice = sc.nextInt();

                if (choice == 0) {
                    System.out.println("프로그램 종료");
                    running = false;
                } else if (choice == 5) {
                    if (cart.isEmpty()) {
                        System.out.println("장바구니가 비어있습니다.");
                        continue;
                    }

                    int total = 0;
                    System.out.println("\n===== 주문 내역 =====");
                    for (String item : cart.keySet()) {
                        int quantity = cart.get(item);
                        int price = priceMap.get(item);
                        int sum = quantity * price;
                        total += sum;
                        System.out.printf("%s x%d = %d원\n", item, quantity, sum);
                    }

                    System.out.println("----------------------");
                    System.out.println("총 금액: " + total + "원");
                    System.out.print("결제하시겠습니까? (1:예 / 2:아니오) > ");
                    int pay = sc.nextInt();

                    if (pay == 1) {
                        // 재고 차감
                        for (String item : cart.keySet()) {
                            stockMap.put(item,
                                stockMap.get(item) - cart.get(item));
                        }
                        cart.clear();
                        System.out.println("결제 완료");
                    }
                } else if (menu.containsKey(choice)) {
                    String selectedItem = menu.get(choice);

                    if (stockMap.get(selectedItem) <= 0) {
                        System.out.println("재고가 부족합니다.");
                        continue;
                    }

                    System.out.print("수량 입력 > ");
                    int quantity = sc.nextInt();

                    if (quantity <= 0) {
                        System.out.println("1개 이상 입력하세요.");
                        continue;
                    }

                    if (quantity > stockMap.get(selectedItem)) {
                        System.out.println("재고보다 많이 주문할 수 없습니다.");
                        continue;
                    }

                    cart.put(selectedItem,
                        cart.getOrDefault(selectedItem, 0) + quantity);

                    System.out.println("장바구니에 담겼습니다.");
                } else {
                    System.out.println("잘못된 선택입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }

        sc.close();
    }

}
