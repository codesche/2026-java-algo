package dailyalgo;

/**
 * Map을 활용하여 작성
 */

import java.util.*;

public class ValidParenthesesEx2 {

    public static void main(String[] args) {
        String s = "()[]{}";

        System.out.println(isValid(s));
    }

    private static boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != map.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

}
