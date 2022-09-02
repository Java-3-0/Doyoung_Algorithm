// 11524KB, 72ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // N 입력
        final int N = Integer.parseInt(br.readLine());

        // dp[i]는 i번 버튼을 눌러서 얻을 수 있는 최대 A 개수
        long[] dp = new long[N + 1];

        // dp 수행
        for (int i = 1; i <= N; i++) {
            long write1 = dp[i - 1] + 1L; // 그냥 A 누르기
            long copy1 = 2L * dp[max0(i - 3)]; // 3턴 전에서 복사한 걸 1번 붙여넣기
            long copy2 = 3L * dp[max0(i - 4)]; // 4턴 전에서 복사한 걸 2번 붙여넣기
            long copy3 = 4L * dp[max0(i - 5)]; // 5턴 전에서 복사한 걸 3번 붙여넣기

            // 최댓값 계산해서 dp 배열에 저장
            long max1 = Math.max(write1, copy1);
            long max2 = Math.max(copy2, copy3);
            dp[i] = Math.max(max1, max2);
        }

        // 출력
        long answer = dp[N];
        System.out.println(answer);

    } // end main

    public static int max0(int num) {
        return Math.max(num, 0);
    }
}