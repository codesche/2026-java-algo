package dailyalgo;

import java.util.*;

/**
 * 1. 문제 이해
 * - 과학자의 H-Index를 나타내는 값 h 구하기
 * - n편 중 h번 이상 인용된 논문이 h편 이상
 * - 나머지 논문이 n번 이하 인용될 경우 h의 최댓값이 과학자의 H-Index
 *
 * 2. 알고리즘 설계
 * - 목표 시간복잡도: O(n)
 * - 입출력 설명
 * In: [3, 0, 6, 1, 5]
 * Out: 3
 * [해당 과학자가 발표한 논문은 5편
 * , 그중 3편의 논문은 3회 이상 인용 -> (3, 6, 5)
 * , 나머지 2편의 논문은 3회 이하 인용되었기 때문에
 * , 이 과학자의 H-Index는 3
 * (최소한의 인용횟수)
 *
 * - 제한 조건
 * 1) 논문의 횟수는 1편 이상 1,000편 이하
 * 2) 논문별 인용 횟수는 0회 이상 10,000회 이하
 *
 * 3. 해결방법
 * - 빠른 비교를 위해 오름차순으로 정렬
 * - 정렬 후 citations[i] >= (n - i)를 만족하는 최댓값 찾기
 * - i번째 논문부터 끝까지 -> (n - i)개, n - i개의 논문이 citations[i] 이상 인용
 */

public class HindexEx {

    public int solution(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;

        for (int i = 0; i < n; i++) {
            int h = n - i;

            if (citations[i] >= h) {
                return h;
            }
        }

        return 0;
    }

}
