// 123584KB, 696ms

package bj15724;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int H, W;
	static int[][] grid;
	static int[][] psums;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 메모리 할당
		grid = new int[H + 1][W + 1];
		psums = new int[H + 1][W + 1];

		// 그리드 입력
		for (int r = 1; r <= H; r++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int c = 1; c <= W; c++) {
				grid[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		// psums 계산
		for (int r = 1; r <= H; r++) {
			for (int c = 1, sum = 0; c <= W; c++) {
				sum += grid[r][c];
				psums[r][c] = sum;
				psums[r][c] += psums[r - 1][c];
			}
		}

		// 쿼리 개수 입력
		int K = Integer.parseInt(br.readLine());

		// 쿼리 수행
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r1 = Integer.parseInt(st.nextToken());
			int c1 = Integer.parseInt(st.nextToken());
			int r2 = Integer.parseInt(st.nextToken());
			int c2 = Integer.parseInt(st.nextToken());

			int answer = getSum(r1, c1, r2, c2);

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	private static int getSum(int r1, int c1, int r2, int c2) {
		return psums[r2][c2] - psums[r1 - 1][c2] - psums[r2][c1 - 1] + psums[r1 - 1][c1 - 1];
	}

}