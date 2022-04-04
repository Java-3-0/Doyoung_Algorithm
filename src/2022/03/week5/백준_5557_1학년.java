package bj5557;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100;
	static final int MAX_NUM = 20;

	static int N;
	static int[] seq = new int[MAX_N + 1];
	static int target;
	static long[][] dp = new long[2][MAX_NUM + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N - 1; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		target = Integer.parseInt(st.nextToken());

		// dp 배열 시작 상태
		dp[0][seq[0]] = 1L;

		// dp 갱신 수행
		int prev = 0;
		int cur = 1;
		for (int idx = 1; idx < N - 1; idx++) {
			int seqVal = seq[idx];
			cur = (prev + 1) % 2;
			Arrays.fill(dp[cur], 0L);

			for (int num = 0; num <= MAX_NUM; num++) {
				if (num + seqVal <= MAX_NUM) {
					dp[cur][num] += dp[prev][num + seqVal];
				}
				if (num - seqVal >= 0) {
					dp[cur][num] += dp[prev][num - seqVal];
				}
			}

			prev = cur;
		}

		// 정답 출력
		long answer = dp[cur][target];

		System.out.println(answer);

	} // end main
}