package datatype;

// =====================================================
// Java Set 자료구조 실습 예제
// HashSet / LinkedHashSet / TreeSet 비교
// =====================================================

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetEx {

    public static void main(String[] args) {

        // ─────────────────────────────────────────
        // 1. HashSet — 중복 제거, 순서 보장 없음
        // ─────────────────────────────────────────
        System.out.println("=== HashSet ===");
        Set<String> hashSet = new HashSet<>();

        hashSet.add("banana");
        hashSet.add("apple");
        hashSet.add("cherry");
        hashSet.add("apple");       // 중복 -> 무시됨
        hashSet.add("date");

        System.out.println("크기: " + hashSet.size());                      // 4 (중복 제거)
        System.out.println("내용: " + hashSet);                             // 순서 랜덤
        System.out.println("포함 여부: " + hashSet.contains("apple"));      // true

        hashSet.remove("banana");
        System.out.println("banana 제거 후: " + hashSet);

        // ─────────────────────────────────────────
        // 2. LinkedHashSet — 삽입 순서 유지
        // ─────────────────────────────────────────
        System.out.println("\n=== LinkedHashSet ===");
        Set<String> linkedHashSet = new LinkedHashSet<>();

        linkedHashSet.add("banana");
        linkedHashSet.add("apple");
        linkedHashSet.add("cherry");
        linkedHashSet.add("apple");     // 중복 -> 무시됨
        linkedHashSet.add("date");

        System.out.println("내용(삽입 순서 유지): " + linkedHashSet);
        // [banana, apple, cherry, date] — 삽입 순서 그대로

        // ─────────────────────────────────────────
        // 3. TreeSet — 자동 정렬 (기본: 오름차순)
        // ─────────────────────────────────────────
        System.out.println("\n=== TreeSet ===");
        TreeSet<String> treeSet = new TreeSet<>();

        treeSet.add("banana");
        treeSet.add("apple");
        treeSet.add("cherry");
        treeSet.add("apple");       // 중복 -> 무시됨
        treeSet.add("date");

        // TreeSet 전용 탐색 메서드
        System.out.println("첫 번째 요소: " + treeSet.first());       // apple
        System.out.println("마지막 요소: " + treeSet.last());         // date
        System.out.println("cherry 이전: " + treeSet.lower("cherry")); // banana
        System.out.println("cherry 이후: " + treeSet.higher("cherry")); // date
        System.out.println("apple~cherry 범위: " + treeSet.subSet("apple", true, "cherry", true));

        // 내림차순 정렬
        TreeSet<Integer> numSet = new TreeSet<>(Comparator.reverseOrder());
        numSet.addAll(Arrays.asList(5, 3, 8, 1, 9, 2));
        System.out.println("\n내림차순 TreeSet: " + numSet);    // [9, 8, 5, 3, 2, 1]

        // ─────────────────────────────────────────
        // 4. Set 집합 연산 (합집합 / 교집합 / 차집합)
        // ─────────────────────────────────────────
        System.out.println("\n=== 집합 연산 ===");
        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(3, 4, 5, 6, 7));

        // 합집합 (union)
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("합집합: " + new TreeSet<>(union)); // [1,2,3,4,5,6,7]

        // 교집합 (intersection)
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("교집합: " + new TreeSet<>(intersection)); // [3,4,5]

        // 차집합 (difference) A - B
        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("차집합 (A-B): " + new TreeSet<>(difference)); // [1,2]

        // ─────────────────────────────────────────
        // 5. 커스텀 객체 — equals/hashCode 재정의
        // ─────────────────────────────────────────
        System.out.println("\n=== 커스텀 객체 Set ===");
        Set<Student> students = new HashSet<>();

        students.add(new Student(1, "Alice"));
        students.add(new Student(2, "Bob"));
        students.add(new Student(1, "Alice"));
        students.add(new Student(3, "Charlie"));

        System.out.println("학생 수: " + students.size());
        for (Student s : students) {
            System.out.println("  " + s);
        }
    }

}


// ─────────────────────────────────────────
// 커스텀 객체: equals & hashCode 재정의 필수
// ─────────────────────────────────────────
class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return this.id == other.id;  // id가 같으면 동일 학생
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }

}












