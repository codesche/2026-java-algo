package algorithm;

// 정렬된 배열에서 합이 target인 두 수 찾기

/**
 * 학습 포인트:
 * - O(n^2) -> O(n) 최적화
 * - 투 포인터 이동 전략
 */

/**
 * 정렬된 배열에서 양쪽 포인터를 이동하며 탐색
 * sum < target -> left++
 * sum > target -> right--
 */

public class TwoPointerEx {

    public static void main(String[] args) {

        int[] arr = {1, 3, 5, 7, 9, 11};
        int target = 16;

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                System.out.println("정답 발견: " + arr[left] + " + " + arr[right]);
                break;
            }

            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }

}
