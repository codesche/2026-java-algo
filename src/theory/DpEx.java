package theory;

/**
 * 피보나치 수열 (Top-Down + Bottom-Up 비교)
 * F(n) = F(n-1) + F(n-2)
 */

import java.util.*;

public class DpEx {

    static long[] memo;

    public static void main(String[] args) {
        int n = 10;
        memo = new long[n + 1];

        System.out.println("피보나치(" + n + ") = " + fib(n));
    }

    static long fib(int n) {
        if (n <= 1) return n;

        if (memo[n] != 0) return memo[n];

        memo[n] = fib(n - 1) + fib(n - 2);
        return memo[n];
    }

}
