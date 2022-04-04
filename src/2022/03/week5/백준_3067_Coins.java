// 12052KB, 104ms

package bj3067;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 20;
	static final int MAX_MONEY = 10000;

	static int N;
	static int[] coins = new int[MAX_N];
	static long[] dp = new long[MAX_MONEY + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// dp배열 초기화
			Arrays.fill(dp, 0);

			// 동전 개수 입력
			N = Integer.parseInt(br.readLine());

			// 동전 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				coins[i] = Integer.parseInt(st.nextToken());
			}

			// 목표 금액 입력
			int targetMoney = Integer.parseInt(br.readLine());

			// dp 수행
			for (int i = 0; i < N; i++) {
				int coin = coins[i];

				dp[0] = 1;
				for (int k = 0; k <= MAX_MONEY; k++) {
					if (k - coin >= 0) {
						dp[k] = dp[k] + dp[k - coin];
					}
				}
			}

			// 정답을 출력 스트링빌더에 추가
			long answer = dp[targetMoney];
			sb.append(answer).append("\n");

		}

		// 출력
		System.out.print(sb.toString());

	} // end main

}