// 14444KB, 144ms

package bj17845;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000, MAX_K = 1000;
	static int N, K;
	static int[] dp = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 중요도와 필요 공부시간을 입력받고, dp 수행
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int importance = Integer.parseInt(st.nextToken());
			int timeRequired = Integer.parseInt(st.nextToken());

			for (int time = N; time >= 0; time--) {
				if (time < timeRequired) {
					break;
				}

				dp[time] = Math.max(dp[time], dp[time - timeRequired] + importance);
			}
		}

		int answer = dp[N];

		System.out.println(answer);

	} // end main

}