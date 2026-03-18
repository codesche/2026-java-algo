

## 전체 설계 구조
Controller (명령) -> Service (비즈니스 로직) -> Hardware Abstraction Layer (Motor, Senser, SafetySystem)

## 핵심 설계 포인트
1. 인터페이스 기반 설계 -> 교체 가능
2. 상태 기반 제어 -> 안전성 확보
3. 예외 흐름 -> 장애 대응
4. Thread-safe 고려 (기본 구조 포함)
