package dailyalgo;

import java.util.*;

// Day1: 두 수의 합이 target이 되는 인덱스 찾기
// 시간복잡도: O(n)

/**
 * 더 좋은 방법
 * => target - 현재값 = 필요한 값
 */

public class TwoSumEx {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];      // 누적 합 개념을 활용

            if (map.containsKey(complement)) {
                System.out.println(map.get(complement) + " " + i);
                return;
            }

            map.put(nums[i], i);
        }

        System.out.println("정답 없음");
    }

}
