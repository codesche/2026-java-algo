package theory;

import java.util.*;

/**
 * 정렬 알고리즘
 */
public class SortAlgoEx {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2};

        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));

        int[] arr2 = {5, 3, 8, 4, 2};

        insertionSort(arr2);
        System.out.println(Arrays.toString(arr2));

        int[] arr3 = {5, 3, 8, 4, 2};

        mergeSort(arr3, 0, 4);
        System.out.println(Arrays.toString(arr3));

        int[] arr4 = {5, 3, 8, 4, 2};

        quickSort(arr4, 0, 4);
        System.out.println(Arrays.toString(arr4));
    }

    // 시간복잡도: O(n^2)
    // 만약 정렬된 경우라면 O(n)
    static void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swapped = true;
                }
            }

            // 이미 정렬되었으면 종료
            if (!swapped) break;
        }
    }

    // 삽입 정렬
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // Merge Sort

    /**
     * 시간복잡도: O(n log n)
     * 안정 정렬
     * 추가 메모리 필요 O(n)
     * @param arr
     * @param left
     * @param right
     */
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // Quick Sort
    static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivotIndex = partition(arr, left, right);

        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, right);
        return i + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
