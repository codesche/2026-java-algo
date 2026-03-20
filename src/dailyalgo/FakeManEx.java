package dailyalgo;

/**
 * <위장>
 * 1. 문제 이해
 * - 스파이가 여러 종류의 옷을 가지고 있다
 *
 * (예시 입력)
 * [["yellow_hat", "headgear"],
 * ["blue_sunglasses", "eyewear"],
 * ["green_turban", "headgear"]]
 *
 * (구조)
 * [옷이름, 옷종류]
 *
 * (옷 종류)
 * headgear, eyewear
 *
 * (조건)
 * - 같은 종류 옷은 1개만 착시 가능
 * - 옷은 안 입을 수도 있음
 * - 적어도 1개는 입어야 함
 *
 * 2. 예시 분석
 * (headgear)
 * yellow_hat, green_turban => 2개
 *
 * (eyewear)
 * blue_sunglasses => 1개
 *
 * {가능한 경우}
 * headgear
 * - 안입음, yellow_hat, green_turban (3가지)
 *
 * eyewear
 * - 안입읍, blue_sunglasses (2가지)
 *
 * 전체 조합: 3 * 2 = 6
 * -> 하지만 아무것도 안 입는 경우 제외 (6 - 1 = 5)
 *
 * {공식}
 * [(종류1 개수 + 1) * (종류2 개수 + 1)] - 1
 *
 * 3. 알고리즘 설계
 * [자료구조]
 * HashMap<String, Integer>
 * key - 옷 종류, value - 개수
 *
 * [알고리즘 흐름]
 * 1. 옷 종류별 개수 저장
 * 2. (개수 + 1) 곱하기
 * 3. 마지막에 -1
 *
 * [시간복잡도]
 * O(N)
 * - Map 삽입 -> O(1)
 * - Map 순회 -> O(K)
 *
 * (* Combination Multiplication Pattern *)
 * -> (종류1 + 1) * (종류2 + 1) * (종류3 + 1) - 1
 * -> 각 종류마다 "안 입는 경우"가 존재
 */

import java.util.*;

public class FakeManEx {

    public int solution(String[][] clothes) {

        Map<String, Integer> map = new HashMap<>();

        // 1. 옷 종류별 개수 카운트
        for (String[] cloth : clothes) {
            map.put(cloth[1], map.getOrDefault(cloth[1], 0) + 1);
        }

        int answer = 1;

        // 2. (개수 + 1) 곱하기
        // [(종류1 개수 + 1) * (종류2 개수 + 1)] => 누적으로 표현
        for (int count : map.values()) {
            answer *= (count + 1);
        }

        // 3. 아무 것도 안 입는 경우 제거
        return answer - 1;
    }

}
