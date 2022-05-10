// 11716KB, 96ms

package bj24417;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MOD = 1000000007;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int[] dp = new int[3];
		dp[0] = 0;
		dp[1] = 1;
		dp[2] = 1;

		for (int i = 3; i <= N; i++) {
			int cur = i % 3;
			dp[cur] = dp[(cur + 1) % 3] + dp[(cur + 2) % 3];
			dp[cur] %= MOD;
		}

		int code1 = dp[N % 3];
		int code2 = N - 2;

		System.out.printf("%d %d\n", code1, code2);

	} // end main
}