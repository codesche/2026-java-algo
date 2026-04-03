package exercise.backtothebasic;

public class BasicCalculate {

    public static void main(String[] args) {
        int[] arr = {3, 7, 2, 9, 5};

        int max = arr[0];
        int min = arr[0];
        int sum = arr[0];

        int maxIndex = 0;
        int minIndex = 0;

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];

            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }

            if (arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
        }

        double avg = (double) sum / arr.length;

        System.out.println("최댓값: " + max + " (index: " + maxIndex + ")");
        System.out.println("최솟값: " + min + " (index: " + minIndex + ")");
        System.out.println("합계: " + sum);
        System.out.println("평균: " + avg);
    }

}
