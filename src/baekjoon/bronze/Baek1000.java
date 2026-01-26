package baekjoon.bronze;

import java.io.*;
import java.util.StringTokenizer;

// 브론즈 5, A + B
public class Baek1000 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        sb.append(A + B);
        System.out.print(sb);
    }
}
