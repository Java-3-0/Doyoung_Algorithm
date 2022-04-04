// 13936KB, 144ms

package bj2629;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 30;
	static final int MAX_M = 7;
	static final int MAX_WEIGHT = 40000;

	static int N, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 추 개수 입력
		N = Integer.parseInt(br.readLine());

		// 추 무게 입력
		st = new StringTokenizer(br.readLine(), " ");
		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// dp 수행
		boolean[][] dp = new boolean[MAX_WEIGHT + 1][2];
		int prev = 0;
		int now = 1;
		dp[0][0] = true;
		dp[0][1] = true;

		for (int num : seq) {
			int tmp = prev;
			prev = now;
			now = tmp;

			for (int i = 0; i <= MAX_WEIGHT; i++) {
				dp[i][now] |= dp[i][prev];

				if (0 <= i + num && i + num <= MAX_WEIGHT) {
					dp[i + num][now] |= dp[i][prev];
				}
				if (isInRange(i - num)) {
					dp[i - num][now] |= dp[i][prev];
				}
				if (isInRange(i + num)) {
					dp[i + num][now] |= dp[i][prev];
				}
				if (isInRange(num - i)) {
					dp[num - i][now] |= dp[i][prev];
				}

			}
		}

		// 구슬 개수 입력
		M = Integer.parseInt(br.readLine());

		// 구슬 무게 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			int weight = Integer.parseInt(st.nextToken());

			char answer = dp[weight][now] ? 'Y' : 'N';

			sb.append(answer).append(" ");
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

	public static boolean isInRange(int weight) {
		if (0 <= weight && weight <= MAX_WEIGHT) {
			return true;
		}

		return false;
	}
}