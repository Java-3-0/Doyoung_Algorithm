// 11484KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static final int IMPOSSIBLE = -1;
    public static int A = IMPOSSIBLE, B = IMPOSSIBLE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine(), " ");
        int D = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        for (int pick = K; pick >= K / 2; pick--) {
            solve(D - 2, pick, K);
        }

        sb.append(A).append("\n").append(B).append("\n");

        System.out.print(sb.toString());

    } // end main

    public static void solve(int day, int next, int next2) {
        int now = next2 - next;

        if (now <= 0) {
            return;
        }

        if (day == 1) {
            if (now <= next) {
                A = now;
                B = next;
            }

            return;
        }

        solve(day - 1, now, next);
    }


}