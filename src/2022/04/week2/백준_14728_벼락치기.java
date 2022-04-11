// 13280KB, 112ms

package bj14728;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100, MAX_T = 10000;
	static int N, T;
	static int[] dp = new int[MAX_T + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, T 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());

		// 공부 시간과 배점을 입력받고, dp 수행
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int timeRequired = Integer.parseInt(st.nextToken());
			int score = Integer.parseInt(st.nextToken());

			for (int time = T; time >= 0; time--) {
				if (time < timeRequired) {
					break;
				}

				dp[time] = Math.max(dp[time], dp[time - timeRequired] + score);
			}
		}

		// 정답 출력
		int answer = dp[T];

		System.out.println(answer);

	} // end main

}