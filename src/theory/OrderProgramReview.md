

# 📘 Java 코드 분석 실습 – 주문 데이터 분석 시스템 해부

## 🎯 실습 목표

- Stream 내부 동작 이해
- groupingBy 자료구조 분석
- Comparator 체이닝 원리 이해
- Optional 설계 의도 파악
- 병렬 스트림 동작 추론
- 다른 언어로 사고 확장

---

# 1️⃣ Stream 내부 동작 분석

```
orders.stream()
.collect(Collectors.groupingBy(
Order::getCustomer,
Collectors.summingDouble(Order::totalPrice)
      ));
```

## 🔍 실행 흐름

1. `orders.stream()` → Stream Pipeline 생성
2. `groupingBy()` → Collector 객체 생성
3. `collect()` → Terminal Operation 실행
4. 내부 반복 시작 (for-loop와 유사)
5. HashMap 생성
6. key(customer) 기준으로 값 누적

## 🧠 핵심 개념

- Stream은 **Lazy Evaluation**
- 중간 연산(map/filter)은 즉시 실행되지 않음
- Terminal 연산에서 한 번에 실행됨

---

# 2️⃣ groupingBy의 실제 내부 구조

기본 구조:

```
newHashMap<K,List<V>>
```

이번 코드에서는:

```
HashMap<String,Double>
```

왜냐하면 `Collectors.summingDouble()`을 사용했기 때문.

## 📌 내부 동작을 단순화하면

```
Map<String,Double>map=newHashMap<>();

for (Orderorder :orders) {
map.put(
order.getCustomer(),
map.getOrDefault(order.getCustomer(),0.0)
+order.totalPrice()
    );
}
```

### 결론

> Stream은 고급 문법으로 감싼 for-loop이다.
>

---

# 3️⃣ Comparator 체이닝 분석

```
Comparator.comparing(Order::totalPrice)
.reversed()
.thenComparing(Order::getQuantity)
```

## 내부 로직을 풀어쓰면

```
intresult=Double.compare(o2.totalPrice(),o1.totalPrice());

if (result==0) {
result=Integer.compare(o1.getQuantity(),o2.getQuantity());
}

returnresult;
```

## 📌 핵심 포인트

- 다중 정렬 조건 구현
- Java의 `List.sort()`는 TimSort (안정 정렬)
- 같은 값이면 기존 순서 유지

---

# 4️⃣ Optional 설계 의도

```
Optional<String>mostSoldProduct= ...
```

## 전통 방식

```
Stringresult=null;
```

### 문제점

- NPE 위험
- null 체크 강제
- 의미 전달이 약함

## Optional의 장점

```
ifPresent()
orElse()
orElseThrow()
```

> "값이 없을 수 있다"는 것을 타입으로 강제
>

---

# 5️⃣ List.of()의 의미

```
List<Order>orders=List.of(...)
```

## 특징

- 불변 리스트
- add/remove 불가
- 수정 불가

## 왜 불변인가?

- 함수형 사고 강화
- 멀티스레드 안전성 향상
- 예측 가능성 증가

---

# 6️⃣ 병렬 스트림으로 변경 시

```
orders.parallelStream()
```

## 문제점

- groupingBy는 기본적으로 공유 Map 사용
- 동시 수정 이슈 발생 가능
- Collector가 thread-safe해야 함

## 안전한 방법

```
Collectors.groupingByConcurrent(...)
```

---

# 7️⃣ 다른 언어로 사고 확장

## Python

```
fromcollectionsimportdefaultdict

total=defaultdict(float)

foroinorders:
total[o.customer]+=o.quantity*o.price
```

## JavaScript

```
orders.reduce((acc,o) => {
acc[o.customer]= (acc[o.customer]||0)+o.quantity*o.price;
returnacc;
}, {});
```

## Kotlin

```
orders.groupBy {it.customer }
      .mapValues {it.value.sumOf {o->o.totalPrice() } }
```

---

# 8️⃣ 시간복잡도 분석

| 연산       | 시간복잡도 |
| ---------- | ---------- |
| groupingBy | O(n)       |
| 정렬       | O(n log n) |
| 평균 계산  | O(n)       |
| Map 조회   | 평균 O(1)  |

---

# 9️⃣ 생각 문제 (심화 분석)

1. Stream은 Iterator 기반일까 Spliterator 기반일까?
2. groupingBy의 내부 Map은 항상 HashMap일까?
3. Comparator 체이닝이 많아지면 성능 영향은?
4. Map.getOrDefault는 해시를 몇 번 계산할까?
5. 병렬 스트림은 항상 빠를까?
6. Optional 대신 null을 사용하면 어떤 버그가 발생할 수 있을까?

---

# 🔥 핵심 정리

이 실습 하나에 포함된 개념:

- 객체지향
- 함수형 프로그래밍
- 불변성
- 컬렉션 내부 구조
- 정렬 알고리즘
- 병렬 처리
- 예외 설계
- 타입 안정성