// 26000KB, 240ms

package bj1915;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		int H = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());

		// 그리드 정보 입력
		int[][] grid = new int[H + 1][W + 1];
		for (int y = 1; y <= H; y++) {
			String line = br.readLine();
			for (int x = 1; x <= W; x++) {
				grid[y][x] = line.charAt(x - 1) - '0';
			}
		}

		// dp 수행
		int[][] dp = new int[H + 1][W + 1];

		for (int y = 1; y <= H; y++) {
			for (int x = 1; x <= W; x++) {
				if (grid[y][x] == 0) {
					dp[y][x] = 0;
				} else {
					int left = dp[y][x - 1];
					int up = dp[y - 1][x];
					int diagonal = dp[y - 1][x - 1];
					dp[y][x] = Math.min(Math.min(left, up), diagonal) + 1;
				}
			}
		}

		// 최대 넓이 계산
		int answer = 0;
		for (int y = 1; y <= H; y++) {
			for (int x = 1; x <= W; x++) {
				int square = dp[y][x] * dp[y][x];
				answer = Math.max(answer, square);
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

}