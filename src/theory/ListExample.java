package theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * List: 순서가 있는 데이터의 집합이고, 중복을 허용한다.
 * 1. 순서가 있음, index 존재
 * 2. 중복 허용, 같은 값 여러 번 저장 가능
 * 3. 동적 크기, 배열과 달리 크기 자동 조절 가능
 * 4. 인터페이스, 구현체(ArrayList, LinkedList 등) 필요
 * => List는 인터페이스다. ArrayList, LinkedList가 구현체다.
 *
 * --- List vs 배열 비교 ---
 * | 구분  | 배열(Array) | List              |
 * | --- | --------- | ----------------- |
 * | 크기  | 고정        | 가변                |
 * | 타입  | 기본형 + 참조형 | 참조형만              |
 * | 메서드 | 없음        | 풍부(add, remove 등) |
 * | 유연성 | 낮음        | 높음                |
 * int[] arr = new int[3];      // 크기 고정
 * List<Integer> list = new ArrayList<>();      // 크기 가변
 *
 * == List 인터페이스 구조 ==
 * Collection (상위) <- List(인터페이스) <- ArrayList / LinkedList / Vector
 * - List는 Collection의 하위 인터페이스
 */

public class ListExample {
    public static void main(String[] args) {

        // 1. List 선언 (인터페이스 -> 구현체)
        List<String> list = new ArrayList<>();

        // 2. 데이터 추가 (중복 허용, 순서 유지)
        list.add("Java");
        list.add("Spring");
        list.add("Java");
        list.add("Database");

        // 3. List 출력
        System.out.println("초기 List: " + list);

        // 4. index 기반 조회
        System.out.println("0번째 요소: " + list.get(0));

        // 5. 특정 위치에 삽입
        list.add(1, "Algorithm");
        System.out.println("삽입 후 List: " + list);

        // 6. 크기 확인
        System.out.println("List 크기: " + list.size());

        // 7. 포함 여부 확인
        System.out.println("Spring 포함? " + list.contains("Spring"));

        // 8. 값으로 삭제
        list.remove("Java");            // 첫 번째 Java만 삭제
        System.out.println("Java 삭제 후: " + list);

        // 9. index로 삭제
        list.remove(0);
        System.out.println("index 0 삭제 후: " + list);

        // 10. 전체 순회 (향상된 for문)
        System.out.println("전체 순회: ");
        for (String s : list) {
            System.out.println(s);
        }

        // 11. 정렬
        Collections.sort(list);
        System.out.println("정렬 후: " + list);

        // 12. 반복 중 안전한 삭제 (Iterator)
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().equals("Spring")) {
                it.remove();        // 안전한 삭제
            }
        }
        System.out.println("Spring 삭제 후: " + list);

        // 13. ArrayList vs LinkedList 선언 예시
        List<Integer> arrayList = new ArrayList<>();        // 조회 빠름
        List<Integer> linkedList = new LinkedList<>();      // 삽입/삭제 빠름

        arrayList.add(1);
        linkedList.add(2);

        // 14. List -> 배열 변환
        String[] arr = list.toArray(new String[0]);
        System.out.println("배열 변환 결과: " + Arrays.toString(arr));

        // 15. 배열 -> List 변환
        List<String> newList = new ArrayList<>(Arrays.asList(arr));
        System.out.println("다시 List 변환: " + newList);
    }
}
