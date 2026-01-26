package baekjoon.bronze;

import java.io.*;
import java.util.StringTokenizer;

// 브론즈 5, A - B
public class Baek1001 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        sb.append(A - B);
        System.out.println(sb);
    }
}
