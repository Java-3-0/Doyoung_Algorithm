// 23444KB, 248ms

package bj13398;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;

	static int N;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		seq = new int[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 정답 계산
		int answer = solve();

		// 출력
		System.out.println(answer);

	} // end main

	public static int solve() {
		// dp[0][i]: 지우는 기회를 사용하지 않고 i번 인덱스까지 고려한 최대 합
		// dp[1][i]: 지우는 기회를 사용하고 i번 인덱스까지 고려한 최대 합
		int[][] dp = new int[2][N + 1];

		// dp 수행
		dp[0][0] = 0;
		dp[1][0] = 0;
		boolean existNonnegative = false;
		int maxNum = seq[1];
		for (int i = 1; i <= N; i++) {
			int num = seq[i];
			maxNum = Math.max(maxNum, num);
			if (num >= 0) {
				existNonnegative = true;
			}

			// 지우는 기회를 사용하지 않는 경우
			dp[0][i] = Math.max(0, dp[0][i - 1] + num);

			// 지우는 기회를 사용한 경우
			dp[1][i] = Math.max(0, Math.max(dp[1][i - 1] + num, dp[0][i - 1]));
		}

		// dp 배열에 존재하는 값들 중 최대값을 찾아서 리턴
		int ret = 0;
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i <= N; i++) {
				ret = Math.max(ret, dp[k][i]);
			}
		}

		// 일반적인 경우 dp의 결과값을 리턴
		if (existNonnegative) {
			return ret;
		}
		// 모두 음수인 경우에도 하나는 선택해야 한다.
		else {
			return maxNum;
		}
	}
}