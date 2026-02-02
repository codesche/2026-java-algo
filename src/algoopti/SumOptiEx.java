package algoopti;

// 단일 루프 -> O(n)
// 변수 2개 -> O(1)
public class SumOptiEx {
    public static void main(String[] args) {

        // 테스트 데이터 (음수 포함)
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        int maxSum = Integer.MIN_VALUE;         // 최종 최대 합
        int currentSum = 0;                     // 현재 연속 합

        for (int value : arr) {
            // 이전 합이 도움이 되면 유지, 아니면 새로 시작
            currentSum = Math.max(value, currentSum + value);

            // 최대값 갱신
            maxSum = Math.max(maxSum, currentSum);
        }

        System.out.println("연속 부분 배열의 최대 합: " + maxSum);

    }
}
