package dailyalgo;

import java.util.*;

public class InCompleteRun2 {
    public String solution(String[] participant, String[] completion) {
        Map<Integer, String> map = new HashMap<>();
        int hashSum = 0;        // 해시값의 합

        for (String p : participant) {
            int hash = p.hashCode();
            map.put(hash, p);
            hashSum += hash;
        }

        for (String c : completion) {
            hashSum -= c.hashCode();
        }

        return map.get(hashSum);
    }
}
