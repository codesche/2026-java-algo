package theory;

/**
 * 회의실 배정
 * - 시작 시간과 종료 시간이 주어질 때, 최대 몇 개의 회의를 배정할 수 있는가?
 *
 * 정렬: O(n log n)
 * 탐색: O(n)
 * => 총 O(n log n)
 *
 * 그리디
 * - 지금 당장 가장 좋아 보이는 선택
 * - 정렬 + 선택 패턴이 많음
 */

import java.util.*;

public class GreedyEx {

    static class Meeting {
        int start;
        int end;

        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {

        List<Meeting> meetings = Arrays.asList(
            new Meeting(1, 4),
            new Meeting(3, 5),
            new Meeting(0, 6),
            new Meeting(5, 7),
            new Meeting(3, 8),
            new Meeting(5, 9),
            new Meeting(6, 10),
            new Meeting(8, 11),
            new Meeting(8, 12),
            new Meeting(2, 13),
            new Meeting(12, 14)
        );

        // 종료 시간 기준 정렬 (끝나는 시간이 같으면 시작 시간 빠른 순)
        meetings.sort((a, b) -> {
            if (a.end == b.end) return a.start - b.start;
            return a.end - b.end;
        });

        int count = 0;
        int lastEndTime = 0;

        for (Meeting m : meetings) {
            if (m.start >= lastEndTime) {
                count++;
                lastEndTime = m.end;
            }
        }

        System.out.println("최대 회의 개수: " + count);
    }

}
