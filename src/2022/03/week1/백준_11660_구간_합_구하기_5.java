// 130556KB, 760ms

package bj11660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1024;
	static final int MAX_M = 100000;

	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[][] psum = new int[MAX_N + 1][MAX_N + 1];
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 각 행마다 따로 psum을 구한다.
		for (int y = 1; y <= N; y++) {
			int sum = 0;
			for (int x = 1; x <= N; x++) {
				sum += grid[y][x];
				psum[y][x] = sum;
			}
		}

		// 위의 행의 psum을 누적하여 것을 아래 행의 psum에 더한다.
		for (int y = 1; y <= N; y++) {
			int sum = 0;
			for (int x = 1; x <= N; x++) {
				psum[y][x] += psum[y - 1][x];
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int y1 = Integer.parseInt(st.nextToken());
			int x1 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			
			// 부분합을 이용해서 원하는 구간의 합계 계산
			int answer = psum[y2][x2] - psum[y2][x1 - 1] - psum[y1 - 1][x2] + psum[y1 - 1][x1 - 1];
			
			sb.append(answer).append("\n");
		} // end for testCase
		
		System.out.print(sb.toString());

	} // end main

}