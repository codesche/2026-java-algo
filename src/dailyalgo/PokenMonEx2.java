package dailyalgo;

/**
 * Stream을 활용한 폰켓몬 풀이
 */
import java.util.*;
import java.util.stream.Collectors;

public class PokenMonEx2 {

    public int solution(int[] nums) {
        Set<Integer> set = Arrays.stream(nums)
            .boxed()
            .collect(Collectors.toSet());

        return Math.min(set.size() / 2, nums.length);
    }

}
