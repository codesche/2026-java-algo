package exercise.backtothebasic;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ─────────────────────────────────────────────────────────────────────────────
 *  TTL 로컬 캐시 (Time-To-Live) – 직접 구현 샘플
 *
 *  핵심 아이디어:
 *   - 항목을 저장할 때 만료 시각(System.currentTimeMillis + ttl)을 함께 기록
 *   - get() 호출 시 만료 시각을 비교해 유효한 경우에만 값을 반환
 *   - 백그라운드 스케줄러로 주기적으로 만료 항목을 일괄 정리 (eviction)
 * ─────────────────────────────────────────────────────────────────────────────
 */

public class LocalCaching {

    // =========================================================================
    //  캐시 항목 래퍼
    // =========================================================================
    static class CacheEntry<V> {
        final V value;
        final long expiresAt;       // 만료 시각 (Unix ms)

        CacheEntry(V value, long expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiresAt;
        }

        @Override
        public String toString() {
            long remaining = expiresAt - System.currentTimeMillis();
            return value + " (잔여 " + Math.max(remaining, 0) + "ms)";
        }
    }

    // =========================================================================
    //  TTL 캐시 본체
    // =========================================================================

    static class TtlCache<K, V> {

        private final long defaultTtlMillis;
        private final Map<K, CacheEntry<V>> store = new ConcurrentHashMap<>();
        private final ScheduledExecutorService cleaner =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "cache-cleaner");
                t.setDaemon(true); // JVM 종료를 막지 않도록 데몬 스레드
                return t;
            });

        // 통계
        private final AtomicInteger hitCount = new AtomicInteger();
        private final AtomicInteger missCount = new AtomicInteger();

        /**
         * @param defaultTtlMillis 기본 TTL (밀리초)
         * @param cleanIntervalMs  만료 항목 정리 주기 (밀리초)
         */
        TtlCache(long defaultTtlMillis, long cleanIntervalMs) {
            this.defaultTtlMillis = defaultTtlMillis;
            // 주기적으로 만료 항목 정리
            cleaner.scheduleAtFixedRate(
                this::evictExpired, cleanIntervalMs, cleanIntervalMs, TimeUnit.MILLISECONDS
            );
        }

        // ── 쓰기 ─────────────────────────────────────────────────────────────
        /** 기본 TTL로 저장 */
        void put(K key, V value) {
            put(key, value, defaultTtlMillis);
        }

        /** 항목별 TTL 지정 저장 */
        void put(K key, V value, long ttlMillis) {
            long expiresAt = System.currentTimeMillis() + ttlMillis;
            store.put(key, new CacheEntry<>(value, expiresAt));
        }

        // ── 읽기 ─────────────────────────────────────────────────────────────

        /** 유효한 항목 반환. 만료됐거나 없으면 null */
        V get(K key) {
            CacheEntry<V> entry = store.get(key);
            if (entry == null) {
                missCount.incrementAndGet();
                return null;
            }
            if (entry.isExpired()) {
                store.remove(key);
                missCount.incrementAndGet();
                return null;
            }
            hitCount.incrementAndGet();
            return entry.value;
        }

        /** 만료 포함 원시 항목 조회 (디버깅용) */
        CacheEntry<V> peek(K key) {
            return store.get(key);
        }

        // ── 제거 ─────────────────────────────────────────────────────────────

        void evict(K key)    { store.remove(key); }
        void evictAll()      { store.clear(); }

        /** 만료된 항목을 전부 삭제 (백그라운드 스케줄러가 주기적으로 호출) */
        void evictExpired() {
            Set<K> keys = store.keySet();
            int removed = 0;
            for (K key : keys) {
                CacheEntry<V> e = store.get(key);
                if (e != null && e.isExpired()) {
                    store.remove(key);
                    removed++;
                }
            }
            if (removed > 0) {
                System.out.printf("[cleaner] 만료 항목 %d개 정리%n", removed);
            }
        }

        // ── 조회 ─────────────────────────────────────────────────────────────

        int size()  { return store.size(); }

        void printStats() {
            int hits   = hitCount.get();
            int misses = missCount.get();
            int total  = hits + misses;
            double rate = total == 0 ? 0 : hits * 100.0 / total;
            System.out.printf("  캐시 통계 | 히트 %d / 미스 %d / 히트율 %.1f%%%n",
                hits, misses, rate);
        }

        void shutdown() { cleaner.shutdown(); }
    }

    // =========================================================================
    //  서비스 레이어 예시 – DB 조회를 캐시로 가속
    // =========================================================================
    static class ProductService {

        private final TtlCache<Integer, String> cache;
        private final AtomicInteger dbCalls = new AtomicInteger();

        ProductService(TtlCache<Integer, String> cache) {
            this.cache = cache;
        }

        String getProduct(int id) {
            // 1단계: 캐시 확인
            String cached = cache.get(id);
            if (cached != null) {
                System.out.printf("  [HIT]  id=%-3d → \"%s\"%n", id, cached);
                return cached;
            }

            // 2단계: 캐시 미스 → DB 조회 (시뮬레이션)
            String product = "상품_" + id;
            dbCalls.incrementAndGet();
            System.out.printf("  [MISS] id=%-3d → DB 조회 → \"%s\" 캐시 저장%n", id, product);
            cache.put(id, product);
            return product;
        }

        int dbCallCount() { return dbCalls.get(); }
    }

    // =========================================================================
    //  main
    // =========================================================================
    public static void main(String[] args) throws InterruptedException {

        // ── 시나리오 1: 기본 TTL 동작 ─────────────────────────────────────
        sep("시나리오 1: 기본 TTL 동작");

        // TTL 1.5초, 정리 주기 3초
        TtlCache<String, Integer> cache = new TtlCache<>(1500, 3000);

        cache.put("score", 99);
        cache.put("coins", 500);
        cache.put("lives", 3, 500);  // 이 항목만 TTL 0.5초

        System.out.println("[0ms] score=" + cache.get("score")); // 99
        System.out.println("[0ms] coins=" + cache.get("coins")); // 500
        System.out.println("[0ms] lives=" + cache.get("lives")); // 3

        Thread.sleep(600);
        System.out.println("\n[600ms] lives=" + cache.get("lives")); // null (만료)
        System.out.println("[600ms] score=" + cache.get("score")); // 99 (아직 유효)

        Thread.sleep(1000);
        System.out.println("\n[1600ms] score=" + cache.get("score")); // null (만료)
        System.out.println("[1600ms] coins=" + cache.get("coins")); // null (만료)

        cache.printStats();
        cache.shutdown();

        // ── 시나리오 2: DB 조회 가속 ──────────────────────────────────────
        sep("시나리오 2: DB 조회 가속 (TTL 2초)");

        TtlCache<Integer, String> productCache = new TtlCache<>(2000, 5000);
        ProductService service = new ProductService(productCache);

        System.out.println("▶ 첫 번째 조회 (모두 MISS → DB 호출 발생)");
        service.getProduct(101);
        service.getProduct(102);
        service.getProduct(103);

        System.out.println("\n▶ 두 번째 조회 (모두 HIT → DB 생략)");
        service.getProduct(101);
        service.getProduct(102);
        service.getProduct(103);

        System.out.printf("%n  DB 총 호출 횟수: %d회 (캐시로 3회 절약)%n",
            service.dbCallCount());
        productCache.printStats();

        System.out.println("\n  (2.1초 대기 → TTL 만료)");
        Thread.sleep(2100);

        System.out.println("\n▶ 만료 후 재조회 (다시 MISS)");
        service.getProduct(101);
        System.out.printf("  DB 총 호출 횟수: %d회%n", service.dbCallCount());
        productCache.printStats();
        productCache.shutdown();

        // ── 시나리오 3: 수동 evict ─────────────────────────────────────────
        sep("시나리오 3: 수동 evict / evictAll");

        TtlCache<String, String> sessionCache = new TtlCache<>(60_000, 10_000);
        sessionCache.put("user:alice", "token-A");
        sessionCache.put("user:bob",   "token-B");
        sessionCache.put("user:carol", "token-C");
        System.out.println("저장 후 크기: " + sessionCache.size()); // 3

        // 로그아웃 → 즉시 제거
        sessionCache.evict("user:bob");
        System.out.println("bob 로그아웃 후 크기: " + sessionCache.size()); // 2
        System.out.println("bob 토큰: " + sessionCache.get("user:bob"));   // null

        // 전체 초기화 (서버 재시작 등)
        sessionCache.evictAll();
        System.out.println("전체 evict 후 크기: " + sessionCache.size()); // 0
        sessionCache.shutdown();

        System.out.println("\n✓ 모든 시나리오 완료");
    }

    static void sep(String title) {
        System.out.println("\n" + "─".repeat(52));
        System.out.println("  " + title);
        System.out.println("─".repeat(52));
    }

}
