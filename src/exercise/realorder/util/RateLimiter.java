package exercise.realorder.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Rate Limiter
 * - 동일 사용자 요청 제한
 * - 간단한 보안 (DDOS 방어 개념)
 * - 실무에선 Redis 기반으로 확장 필요
 */
public class RateLimiter {
    private final Map<String, Long> requestLog = new HashMap<>();
    private final long limitMillis;

    public RateLimiter(long limitMillis) {
        this.limitMillis = limitMillis;
    }

    /**
     * 요청 허용 여부 판단
     * @param userId 사용자 ID
     * @return true: 허용, false: 차단
     */
    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        Long lastRequest = requestLog.get(userId);

        // 최초 요청 or 제한 시간 초과 -> 허용
        if (lastRequest == null || now - lastRequest > limitMillis) {
            requestLog.put(userId, now);
            return true;
        }
        return false;
    }

}
