
## 실무 시나리오

사용자가 주문을 하면 포인트가 적립된다.

```md
주문금액: 100,000원

NORMAL -> 1% 적립
VIP -> 3% 적립
BLACK -> 5% 적립

### 추가 조건

1. 첫 구매 이벤트
2. 특정 상품 이벤트
3. 생일 이벤트
4. 프로모션 이벤트

```

## 설계 전략
1. Strategy Pattern + Policy Pattern

```md
PointService
    │
    ├ PointPolicy
    │     ├ GradePointPolicy
    │     ├ FirstOrderPolicy
    │     └ HighAmountPolicy
    │
    └ PointCalculator
```

2. 핵심 철학
- 정책 = 클래스



