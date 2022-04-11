// 21196KB, 336ms

package bj20002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300;
	static final int INF = 987654321;

	static int N;
	static int[][] grid;
	static int[][] psums;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		grid = new int[N + 1][N + 1];
		psums = new int[N + 1][N + 1];

		// 그리드 입력받고 각 행의 psums 계산
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1, sum = 0; x <= N; x++) {
				int input = Integer.parseInt(st.nextToken());
				sum += input;

				grid[y][x] = input;
				psums[y][x] = sum;
			}
		}
		for (int y = 1; y <= N; y++) {
			for (int x = 1; x <= N; x++) {
				psums[y][x] += psums[y - 1][x];
			}
		}

		// 정사각형 범위들의 합들 중 최대값을 계산
		int maxSum = -INF;
		for (int startY = 1; startY <= N; startY++) {
			for (int startX = 1; startX <= N; startX++) {
				for (int d = 0; d <= N; d++) {
					int endY = startY + d;
					int endX = startX + d;

					if (!isInRange(endY, endX)) {
						break;
					}

					int sum = getSum(startY, startX, endY, endX);
					maxSum = maxSum < sum ? sum : maxSum;
				}
			}
		}

		// 출력
		System.out.println(maxSum);

	} // end main

	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= N) {
			return true;
		}

		return false;
	}

	public static int getSum(int startY, int startX, int endY, int endX) {
		return psums[endY][endX] - psums[endY][startX - 1] - psums[startY - 1][endX] + psums[startY - 1][startX - 1];
	}
}