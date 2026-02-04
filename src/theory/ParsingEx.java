package theory;

/**
 * String.split() 사용 안 함 -> 정규식 + 배열 생성 비용 제거
 * StringBuilder 재사용
 * Integer.parseInt() 대신 직접 파싱
 * -> 예외 처리 + 내부 체크 비용 제거
 *
 * char[] 기반 순회
 * -> CPU 캐시 친화적
 */

public class ParsingEx {
    public static void main(String[] args) {
        // 대용량 데이터 시뮬레이션 (실무에선 파일/네트워크에서 옴)
        String rawData =
                "1|Alice|25|88\n" +
                "2|Bob|30|92\n" +
                "3|Charlie|28|76\n" +
                "4|David|35|81\n" +
                "5|Eve|22|95\n";

        int totalScore = 0;
        int count = 0;

        int id = 0;
        int age = 0;
        int score = 0;

        String name = null;

        StringBuilder token = new StringBuilder(32);
        int columnIndex = 0;

        // char 배열로 변환 (charAt 반복 호출보다 빠른 경우 많음)
        char[] data = rawData.toCharArray();

        for (int i = 0; i < data.length; i++) {
            char c = data[i];

            // 구분자(|) 또는 줄 끝(\n)
            if (c == '|' || c == '\n') {

                switch (columnIndex) {
                    case 0:
                        id = parseIntFast(token);
                        break;
                    case 1:
                        name = token.toString();
                        break;
                    case 2:
                        age = parseIntFast(token);
                        break;
                    case 3:
                        score = parseIntFast(token);
                        break;
                }

                token.setLength(0);         // StringBuilder 재사용
                columnIndex++;

                // 한 줄 끝났을 때
                if (c == '\n') {
                    // 실제 비즈니스 로직
                    if (age >= 25) {
                        totalScore += score;
                        count++;
                    }

                    // 다음 레코드 준비
                    columnIndex = 0;
                    id = age = score = 0;
                    name = null;
                }
            } else {
                token.append(c);
            }
        }

        double average = count == 0 ? 0 : (double) totalScore / count;
        System.out.println("25세 이상 평균 점수: " + average);
    }

    // split, Integer.parseInt 안 쓰는 빠른 숫자 파싱
    private static int parseIntFast(StringBuilder sb) {
        int result = 0;
        for (int i = 0; i < sb.length(); i++) {
            result = result * 10 + (sb.charAt(i) - '0');
        }
        return result;
    }

}
