// 15308KB, 232ms

package bj1126;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_HEIGHT = 500000;
	static final int MAX_DIFF = MAX_HEIGHT / 2 + 1;
	static final int IMPOSSIBLE = -1;

	static int N;
	static int[] pieces;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 조각 개수 입력
		N = Integer.parseInt(br.readLine());

		// 조각 높이를 담을 배열 메모리 할당
		pieces = new int[N];

		// 조각들의 높이 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			pieces[i] = Integer.parseInt(st.nextToken());
		}

		// dp 배열 선언(dp[now][i]는 두 탑의 높이 차이가 i일 때, 큰 쪽 탑의 최대 높이)
		int[][] dp = new int[2][MAX_DIFF + 1];

		// dp 배열 초기화
		for (int i = 0; i < dp.length; i++) {
			Arrays.fill(dp[i], IMPOSSIBLE);
		}
		dp[0][0] = 0;

		// dp 수행
		int prev = 0;
		int now = 1;
		for (int i = 0; i < N; i++) {
			int piece = pieces[i];
			now = (prev + 1) % 2;
			Arrays.fill(dp[now], IMPOSSIBLE);

			for (int diff = 0; diff <= MAX_DIFF; diff++) {
				if (dp[prev][diff] != IMPOSSIBLE) {
					int big = dp[prev][diff];
					int small = dp[prev][diff] - diff;

					// 큰 쪽에 쌓는 경우
					int nextDiff = diff + piece;
					int nextBig = big + piece;
					if (nextDiff <= MAX_DIFF) {
						dp[now][nextDiff] = Math.max(dp[now][nextDiff], nextBig);
					}

					// 작은 쪽에 쌓는 경우
					nextDiff = Math.abs(diff - piece);
					nextBig = Math.max(big, small + piece);
					if (nextDiff <= MAX_DIFF) {
						dp[now][nextDiff] = Math.max(dp[now][nextDiff], nextBig);
					}

					// 안 쌓는 경우
					nextDiff = diff;
					nextBig = big;
					dp[now][nextDiff] = Math.max(dp[now][nextDiff], nextBig);

				}
			}

			prev = now;
		}

		// 정답 계산
		int answer = dp[now][0];

		// 출력
		if (answer > 0) {
			System.out.println(answer);
		} else {
			System.out.println(-1);
		}

	} // end main
}