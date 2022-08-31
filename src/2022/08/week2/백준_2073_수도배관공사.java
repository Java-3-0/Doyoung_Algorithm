// 12324KB, 132ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_D = 100000;
    static final int MAX_P = 350;
    static final int INF = 987654321;


    static int D, P;
    static int[] lengths = new int[MAX_P];
    static int[] capacities = new int[MAX_P];
    static int[] dp = new int[MAX_D + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br=  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine(), " ");
        D = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            lengths[i] = Integer.parseInt(st.nextToken());
            capacities[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.fill(dp, 0);
        dp[0] = INF;

        for (int i = 0; i < P; i++) {
            int length = lengths[i];
            int capacity = capacities[i];

            for (int pos = D; pos >= length; pos--) {
                dp[pos] = Math.max(dp[pos], Math.min(capacity, dp[pos - length]));
            }
        }

        int answer = dp[D];

        System.out.println(answer);
    }
}
