package theory;

/**
 * String은 불변 객체
 * - 한 번 생성되면 값이 절대 바뀌지 않음
 * - 문자열을 "수정"하는 것처럼 보여도 새 객체가 생성됨
 * - 멀티스레드 환경에서 안전(Thread-Safe)
 *
 * == String Pool ==
 * - "hello" 같은 리터럴은 String Pool에 저장
 * - 동일한 문자열은 같은 객체 참조
 *
 * == 문자열 비교는 무조건 equals() ==
 * 1. == : 참조 비교 x
 * 2. equals(): 값 비교
 * 3. 문자열을 많이 바꿀 땐 StringBuilder / StringBuffer
 */
public class StringExample {
    public static void main(String[] args) {

        /* ===============================
           1. String 생성 방식 & 비교
           =============================== */
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");

        System.out.println("== 비교 (참조 비교) ==");
        System.out.println(s1 == s2);       // true
        System.out.println(s1 == s3);       // false

        System.out.println("\n equals 비교 (값 비교)");
        System.out.println(s1.equals(s3));  // true

        /* ===============================
           2. 불변성 확인
           =============================== */
        String text = "hello";
        text.concat(" world");      // 반영 안 됨
        System.out.println("\n불변성 확인: " + text);

        text = text.concat(" world");
        System.out.println("변경 후: " + text);

         /* ===============================
           3. 자주 쓰는 String 메서드
           =============================== */
        String email = "  user@test.com  ";

        System.out.println("\nlength(): " + email.length());
        System.out.println("trim(): [" + email.trim() + "]");
        System.out.println("toUpperCase(): " + email.toUpperCase());
        System.out.println("contains(\"@\"): " + email.contains("@"));
        System.out.println("startsWith(\"user\"):" + email.trim().startsWith("user"));
        System.out.println("endsWith(\".com\"): " + email.trim().endsWith(".com"));

        /* ===============================
           4. substring (현업에서 자주 씀)
           =============================== */
        String 주민번호 = "900101-1234567";

        String 생년월일 = 주민번호.substring(0, 6);
        String 성별코드 = 주민번호.substring(7, 8);

        System.out.println("\n생년월일: " + 생년월일);
        System.out.println("성별코드: " + 성별코드);

        /* ===============================
           5. split (CSV, 로그 파싱)
           =============================== */
        String csv = "apple,banana,orange";
        String[] fruits = csv.split(",");

        System.out.println("\nsplit 결과:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        /* ===============================
           6. replace (마스킹 처리)
           =============================== */
        String phone = "010-1234-5678";
        String masked = phone.replace("1234", "****");

        System.out.println("\n전화번호 마스킹: " + masked);

        /* ===============================
           7. 문자열 검증 로직 (현업 느낌)
           =============================== */
        String input = " ";

        if (input == null || input.isBlank()) {
            System.out.println("\n입력값이 비어있습니다.");
        }

        /* ===============================
           8. StringBuilder (성능 중요)
           =============================== */
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= 5; i++) {
            sb.append("번호: ").append(i).append("\n");
        }

        String result = sb.toString();
        System.out.println("\nStringBuilder 결과:");
        System.out.println(result);

        /* ===============================
           9. 실전 예제: URL 파라미터 파싱
           =============================== */
        String url = "https://example.com?user=kim&age=30";

        String query = url.substring(url.indexOf("?") + 1);
        String[] params = query.split("&");

        System.out.println("URL 파라미터:");
        for (String param : params) {
            String[] keyValue = param.split("=");
            System.out.println(keyValue[0] + " = " + keyValue[1]);
        }
    }
}
