package dailyalgo;

/**
 * === 완주하지 못한 선수 ===
 * [문제 요약]
 * 마라톤 참가자 목록 paricipant와 완주한 사람 목록 completion 이 주어진다.
 * 이 중에서 완주하지 못한 선수 1명을 찾는 문제이다.
 *
 * [조건]
 * - 참가자 수: 최대 100,000
 * - 동명이인 가능
 *
 * [이 문제의 핵심 알고리즘]
 * - HashMap Counting
 *
 * [사고 과정]
 * 1. 참가자 카운트 - leo(1), kiki(1), eden(1)
 * 2. 완주자 제거 - eden, kiki
 * leo : 1
 * kiki : 0
 * eden : 0
 * => 남은 사람은 leo
 */

import java.util.*;

public class InCompleteRun {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();

        // 참가 선수 Map에 추가
        for (String p : participant) {
            map.put(p, map.getOrDefault(p, 0) + 1);
        }

        // 완주한 선수는 해당 map에서 제외
        for (String c : completion) {
            map.put(c, map.get(c) - 1);
        }

        // 업데이트 된 map의 key를 순회해서 0보다 크면 return 해준다
        for (String key : map.keySet()) {
            if (map.get(key) > 0) {
                return key;
            }
        }

        return "";
    }
}
