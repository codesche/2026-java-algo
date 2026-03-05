package algorithm;

/**
 * 학습 포인트
 * - log n 알고리즘
 * - 검색 알고리즘 핵심
 * - mid 기준으로 탐색 범위를 반으로 줄임
 */
public class BinarySearchEx {

    public static void main(String[] args) {

        int[] arr = {2, 4, 6, 8, 10, 12, 14};
        int target = 10;

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                System.out.println("Index: " + mid);
                return;
            }

            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println("값 없음");
    }

}
