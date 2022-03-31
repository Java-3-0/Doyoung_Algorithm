// 82648KB, 532ms

package bj16507;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 1000, MAX_W = 1000;
	static int H, W, Q;
	static int[][] grid;
	static int[][] psums;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// H, W, Q 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		// 메모리 할당
		grid = new int[H + 1][W + 1];
		psums = new int[H + 1][W + 1];

		// 그리드 입력
		for (int y = 1; y <= H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 각 행마다 따로 psum을 구한다.
		for (int y = 1; y <= H; y++) {
			int sum = 0;
			for (int x = 1; x <= W; x++) {
				sum += grid[y][x];
				psums[y][x] = sum;
			}
		}

		// 위의 행의 psum을 누적하여 것을 아래 행의 psum에 더한다.
		for (int y = 1; y <= H; y++) {
			for (int x = 1; x <= W; x++) {
				psums[y][x] += psums[y - 1][x];
			}
		}

		// 쿼리 수행
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int y1 = Integer.parseInt(st.nextToken());
			int x1 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());

			int sum = psums[y2][x2] - psums[y2][x1 - 1] - psums[y1 - 1][x2] + psums[y1 - 1][x1 - 1];
			int cnt = (y2 - (y1 - 1)) * (x2 - (x1 - 1));

			int avg = sum / cnt;

			sb.append(avg).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

}