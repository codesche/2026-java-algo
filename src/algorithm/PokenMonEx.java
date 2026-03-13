package algorithm;

/**
 * 폰켓몬 - HashSet 패턴의 문제
 *
 * 1. 문제 이해
 * - 포켓몬 종류가 담긴 배열이 주어짐
 * - nums = [3, 1, 2, 3]
 * - 총 포켓몬 수: 4
 * - 선택 가능: N / 2 => 2마리
 * - 목표: 가장 다양한 종류의 포켓몬 선택
 * - 종류: 1 2 3 => 3종류 (하지만 선택 가능한 건 2마리), 정답 = 2
 * - 핵심 알고리즘: 종류 개수(배열의 종류 개수) vs 선택 가능 개수
 * - 정답 공식: min(종류 수, N/2)
 *
 * 2. 알고리즘 설계
 * [자료구조 - HashSet]
 * => 중복 제거
 *
 * [알고리즘]
 * 1. HashSet에 포켓몬 저장
 * 2. 종류 개수 계산
 * 3. N/2 계산
 * 4. min(종류수, N/2)
 *
 * [시간복잡도]
 * O(N)
 *
 * [패턴]
 * HashSet Unique Pattern
 *
 * [문제 유형]
 * - 종류 최대화 문제 => 의상 조합 문제, 서로 다른 문자 찾기, 최대 종류 선택
 *
 */
import java.util.*;

public class PokenMonEx {

    public int solution(int[] nums) {
        int answer = 0;
        Set<Integer> set = new HashSet<>();

        // 1. 포켓몬 종류 저장
        for (int num : nums) {
            set.add(num);
        }

        // 2. 선택 가능 개수
        int count = set.size();

        // 3. 정답 계산
        answer = Math.min(count, nums.length / 2);

        return answer;
    }

}
