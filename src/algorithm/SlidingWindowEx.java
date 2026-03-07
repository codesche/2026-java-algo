package algorithm;

/**
 * 문제: 문자열에서 중복 문자가 없는 가장 긴 부분 문자열의 길이를 구하기
 * 목표 시간복잡도: O(n)
 *
 * Sliding Window
 * 1. 윈도우(부분 구간)을 유지하면서 왼쪽 / 오른쪽 포인터를 이동
 * 2. 흐름
 * - right 포인터 이동
 * - 중복 없으면 확장
 * - 중복 발생하면 left 이동
 * - 최대 길이 갱신
 *
 * 3. Set을 활용하는 이유는 중복을 제거하기 위함
 * - Set은 중복 허용을 하지 않음
 */

import java.util.*;

public class SlidingWindowEx {

    public static void main(String[] args) {
        String s = "abcabcbb";

        System.out.println(lengthOfLongestSubstring(s));
    }

    /**
     * right와 left 변수를 어떻게 활용하는지를 파악하는 게 중요
     * left: 축소
     * right: 확장
     * => window를 늘리다가 문제가 생기면 줄인다!
     *
     * 초기화를 해서 진행할 수도 있지만 그렇게 되면 자원 낭비이기에
     * left를 이동해서 중복을 제거하는 방향으로 진행한다.
     * 하지만 한 번만 이동해가지고는 안 될수도 있기 때문에
     * while을 활용하여 중복이 사라질 때까지 처리해주는 로직을 구성한다.
     *
     * === 패턴 ===
     * 1. right 확장
     * 2. 문제 발생
     * 3. left 이동으로 해결
     */
    private static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();

        int left = 0;
        int maxLength = 0;          // 최대 길이 변수

        for (int right = 0; right < s.length(); right++) {

            // set에 포함이 되어있다면 제거해주고, left를 증가시킨다
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            // set에 먼저 추가를 해주는 로직을 고려하는 게 맞음
            set.add(s.charAt(right));

            // 현재 윈도우 길이 계산과 기존 최대 길이 비교
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

}
