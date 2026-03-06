package dailyalgo;

/**
 * Day2: 올바른 괄호
 * 시간복잡도: O(n)
 */

import java.util.*;

public class ValidParenthesesEx {

    public static void main(String[] args) {

        String s = "()[]{}";

        System.out.println(isValid(s));
    }

    private static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            if (!stack.isEmpty() && c == ')' && stack.peek() == '(') {
                stack.pop();
            } else if (!stack.isEmpty() && c == ']' && stack.peek() == '[') {
                stack.pop();
            } else if (!stack.isEmpty() && c == '}' && stack.peek() == '{') {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

}
