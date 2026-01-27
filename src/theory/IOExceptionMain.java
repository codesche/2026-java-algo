package theory;

/**
 * 입출력은 언제든 실패할 수 있기 때문에, 반드시 예외 처리가 필요하다.
 * 1. 파일이 존재하지 않음
 * 2. 파일 권한 없음
 * 3. 네트워크 끊김
 * 4. 입력 도중 강제 종료
 * 5. 스트림이 이미 닫힘
 * => 그래서 Checked Exception인 IOException 처리를 강제함
 *
 * 왜 br.readLine() 을 쓰는가?
 * - 빠르고 안정적으로 한 줄씩 문자열 입력을 받기 위해서
 *
 * BufferedReader의 장점
 * | 항목     | 설명                        |
 * | ------ | ------------------------- |
 * | 속도     | 내부 버퍼 사용 → `Scanner`보다 빠름 |
 * | 안정성    | 줄 단위 처리 → 입력 제어 쉬움        |
 * | 실무 활용도 | 파일, 콘솔, 네트워크 입력에 널리 사용    |
 *
 * Scanner의 단점
 * 1. 내부적으로 느림
 * 2. 대량 데이터 처리 시 성능 저하
 * 3. 알고리즘/실무에서 거의 BufferedReader 선호
 *
 * readLine()의 특징
 * 1. 입력이 없으면 null 반환
 * 2. 항상 String
 * 3. 엔터 기준으로 한 줄 읽음
 *
 * BufferedReader는 빠른 입력 처리를 위해 사용하며,
 * readLine()은 입출력 오류가 발생할 수 있으므로
 * IOException에 대한 예외 처리가 필요하다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOExceptionMain {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("이름을 입력하세요: ");
            String name = br.readLine();                    // 문자열 입력

            System.out.print("나이를 입력하세요: ");
            int age = Integer.parseInt(br.readLine());      // 문자열 -> 정수 변환

            System.out.println("==== 결과 ====");

            if (!Character.isDigit(name.charAt(0))) {
                System.out.println("이름: " + name);
            } else {
                System.out.println("이름을 다시 입력하세요!");
            }

            if (age > 120) {
                System.out.println("나이를 다시 입력하세요!");
            } else {
                System.out.println("나이: " + age);
            }

        } catch (IOException e) {
            // 입출력 오류 처리
            System.out.println("입력 중 오류가 발생했습니다.");
        } catch (NumberFormatException e) {     // 형식 오류 고려
            // 숫자 변환 오류 처리
            System.out.println("숫자를 올바르게 입력해주세요.");
        }
    }
}













