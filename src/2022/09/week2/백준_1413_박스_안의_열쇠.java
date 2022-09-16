// 11536KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_KEYS = 20, MAX_BOMBS = MAX_KEYS;
    static int KEYS, BOMBS;
    // dp[key][bomb]는 key 개의 열쇠(상자)를 딱 bomb 개의 폭탄으로 얻는 경우의 수
    static long[][] dp = new long[MAX_KEYS + 1][MAX_BOMBS + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 입력
        st = new StringTokenizer(br.readLine(), " ");
        KEYS = Integer.parseInt(st.nextToken());
        BOMBS = Integer.parseInt(st.nextToken());

        // dp 수행
        dp[1][1] = 1;
        for (int k = 1; k <= KEYS; k++) {
            for (int b = 1; b <= k; b++) {
                if (k == 1 && b == 1) {
                    continue;
                }
                dp[k][b] = dp[k - 1][b - 1] + (k - 1) * dp[k - 1][b];
            }
        }

        // 성공 경우의 수 : [0, BOMBS] 개의 폭탄 사용
        long numer = 0L;
        for (int b = 0; b <= BOMBS; b++) {
            numer += dp[KEYS][b];
        }

        // 전체 경우의 수 : [0, KEYS] 개의 폭탄 사용
        long denom = 0L;
        for (int b = 0; b <= KEYS; b++) {
            denom += dp[KEYS][b];
        }

        // 약분해서 기약분수로 나타내기
        long gcd = getGCD(numer, denom);
        numer /= gcd;
        denom /= gcd;

        // 출력
        sb.append(numer).append("/").append(denom).append("\n");
        System.out.print(sb.toString());

    } // end main

    public static long getGCD(long a, long b) {
        if (a == 0L) {
            return b;
        }

        return getGCD(b % a, a);
    }


}