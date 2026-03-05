package algorithm;

import java.util.PriorityQueue;

/**
 * 학습 포인트
 * - Greedy
 * - Heap / PriorityQueue
 * - 스케줄링 문제
 * - 가장 작은 작업부터 처리
 */

public class GreedyPriorityQueueEx {

    public static void main(String[] args) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.add(8);
        pq.add(3);
        pq.add(5);
        pq.add(1);

        int totalTime = 0;
        int currentTime = 0;

        while (!pq.isEmpty()) {
            int job = pq.poll();

            currentTime += job;
            totalTime += currentTime;

            System.out.println("작업 처리 시간: " + job);
        }

        System.out.println("총 대기 시간: " + totalTime);
    }

}
