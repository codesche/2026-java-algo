package theory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 1. SQL Injection 테스트
 * 2. XSS(크로스 사이트 스크립팅) 테스트
 * 3. 비밀번호 해시 처리 (SHA-256)
 * 4. 간단한 입력값 검증
 * 5. 취약한 코드 vs 안전한 코드 비교
 */

/**
 * ```
 * 아이디: admin' --
 * 비밀번호: 아무거나
 * ```
 *
 * ```
 * <script>alert('해킹')</script>
 * ```
 */

public class SecurityTestApp {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== 보안 테스트 실습 시작 =====");

        /**
         * 1. SQL Injection 취약 테스트
         */
        System.out.println("\n[1] SQL Injection 테스트");

        System.out.println("아이디 입력: ");
        String userId = scanner.nextLine();

        System.out.println("비밀번호 입력: ");
        String password = scanner.nextLine();

        // 취약한 SQL 쿼리 (문자열 결합)
        String vulnerableQuery =
            "SELECT * FROM users WHERE user_id = '" + userId +
                "' AND password = '" + password + "'";

        System.out.println("\n[취약한 쿼리]");
        System.out.println(vulnerableQuery);

        // SQL Injection 예시 입력:
        // 아이디: admin' --
        // 비밀번호: 아무거나

        /**
         * 2. 입력값 검증 (화이트리스트 방식)
         */
        System.out.println("\n[2] 입력값 검증 테스트");

        boolean isValid = Pattern.matches("^[a-zA-Z0-9_]{4,12}$", userId);

        if (isValid) {
            System.out.println("아이디 형식 안전");
        } else {
            System.out.println("⚠ 아이디 형식이 보안 정책에 맞지 않습니다.");
        }

        /**
         * 3. XSS 공격 테스트
         */
        System.out.println("\n[3] XSS 테스트");

        System.out.println("댓글 입력: ");
        String comment = scanner.nextLine();

        // 취약한 출력
        System.out.println("\n[취약한 출력]");
        System.out.println(comment);

        // 공격 예시:
        // <script>alert('해킹')</script>

        // 간단한 치환 처리
        String safeComment = comment
            .replace("<", "&lt;")
            .replace(">", "%gt;");

        System.out.println("\n[방어 처리된 출력]");
        System.out.println(safeComment);

        /**
         * 4. 비밀번호 해시 처리
         */
        System.out.println("\n[4] 비밀번호 해시 테스트");

        String hashedPassword = sha256(password);

        System.out.println("원본 비밀번호: " + password);
        System.out.println("SHA-256 해시값: " + hashedPassword);

        /**
         * 5. 안전한 SQL 예시 (PreparedStatement 개념 설명용)
         */
        System.out.println("\n[5] 안전한 SQL 예시 (PreparedStatement)");

        String safeQuery = "SELECT * FROM users WHERE user_id = ? AND password = ?";
        System.out.println(safeQuery);

        System.out.println("\n===== 보안 테스트 실습 종료 =====");
    }

    // SHA-256 해시 함수
    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes());

        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }


}
