// 11860KB, 80ms

package bj24418;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 15;

	static int N;
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[][] dp = new int[MAX_N + 1][MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		N = Integer.parseInt(br.readLine());
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int answer1 = calcAnswer1();
		int answer2 = N * N;

		System.out.printf("%d %d\n", answer1, answer2);

	} // end main

	public static int calcAnswer1() {
		for (int i = 0; i <= N; i++) {
			dp[i][0] = 1;
			dp[0][i] = 1;
		}

		for (int y = 1; y <= N; y++) {
			for (int x = 1; x <= N; x++) {
				dp[y][x] = dp[y - 1][x] + dp[y][x - 1];
			}
		}

		return dp[N][N];
	}

}