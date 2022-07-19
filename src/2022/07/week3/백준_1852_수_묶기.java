// 237312KB, 1444ms

package boj1852;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_ROWS = 100000;
	static final int COLUMNS = 3;
	static final int MAX_BITMASK = (1 << COLUMNS) - 1;
	static final long CACHE_EMPTY = -1L;
	static final long INF = 987654321098765L;

	static int R;
	static int[][] grid = new int[MAX_ROWS][COLUMNS];
	static long[][][] maxCache = new long[MAX_ROWS + 1][MAX_BITMASK + 1][MAX_BITMASK + 1];
	static long[][][] minCache = new long[MAX_ROWS + 1][MAX_BITMASK + 1][MAX_BITMASK + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 캐시 초기화
		initCaches();

		// 행 개수 입력
		R = Integer.parseInt(br.readLine());

		// 그리드 상태 입력
		for (int y = 0; y < R; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < COLUMNS; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 정답 구하기
		long max = getMax(0, 0, 0);
		long min = getMin(0, 0, 0);

		// 출력
		sb.append(max).append("\n").append(min).append("\n");
		System.out.print(sb.toString());

	} // end main

	public static void initCaches() {
		for (int i = 0; i < maxCache.length; i++) {
			for (int k = 0; k < maxCache[i].length; k++) {
				Arrays.fill(maxCache[i][k], CACHE_EMPTY);
			}
		}

		for (int i = 0; i < minCache.length; i++) {
			for (int k = 0; k < minCache[i].length; k++) {
				Arrays.fill(minCache[i][k], CACHE_EMPTY);
			}
		}
	}

	public static long getMax(int rowIdx, int rowUsed, int nextRowUsed) {
		// base case. 끝까지 처리한 경우
		if (rowIdx == R) {
			return 0L;
		}

		// memoization
		if (maxCache[rowIdx][rowUsed][nextRowUsed] != CACHE_EMPTY) {
			return maxCache[rowIdx][rowUsed][nextRowUsed];
		}

		// 새로 계산
		long ret = -INF;

		if (rowUsed == MAX_BITMASK) {
			return maxCache[rowIdx][rowUsed][nextRowUsed] = getMax(rowIdx + 1, nextRowUsed, 0);
		}

		for (int pos = 0; pos < 3; pos++) {
			// 안 묶인 칸의 경우
			if ((rowUsed & (1 << pos)) == 0) {
				// 현재 칸의 값
				int valHere = grid[rowIdx][pos];

				// 아래 칸과 묶을 수 있는 경우
				if (rowIdx + 1 < R) {
					int valUnder = grid[rowIdx + 1][pos];
					int diffUnder = Math.abs(valUnder - valHere);
					ret = Math.max(ret, diffUnder + getMax(rowIdx, (rowUsed | (1 << pos)), (nextRowUsed | (1 << pos))));
				}

				// 오른쪽 칸과 묶을 수 있는 경우
				if (pos + 1 < 3 && ((rowUsed & (1 << (pos + 1))) == 0)) {
					int valRight = grid[rowIdx][pos + 1];
					int diffRight = Math.abs(valRight - valHere);
					ret = Math.max(ret,
							diffRight + getMax(rowIdx, (rowUsed | (1 << pos) | (1 << (pos + 1))), nextRowUsed));
				}
			}
		}

		return maxCache[rowIdx][rowUsed][nextRowUsed] = ret;
	}

	public static long getMin(int rowIdx, int rowUsed, int nextRowUsed) {
		// base case. 끝까지 처리한 경우
		if (rowIdx == R) {
			return 0L;
		}

		// memoization
		if (minCache[rowIdx][rowUsed][nextRowUsed] != CACHE_EMPTY) {
			return minCache[rowIdx][rowUsed][nextRowUsed];
		}

		// 새로 계산
		long ret = INF;

		if (rowUsed == MAX_BITMASK) {
			return minCache[rowIdx][rowUsed][nextRowUsed] = getMin(rowIdx + 1, nextRowUsed, 0);
		}

		for (int pos = 0; pos < 3; pos++) {
			// 안 묶인 칸의 경우
			if ((rowUsed & (1 << pos)) == 0) {
				// 현재 칸의 값
				int valHere = grid[rowIdx][pos];

				// 아래 칸과 묶을 수 있는 경우
				if (rowIdx + 1 < R) {
					int valUnder = grid[rowIdx + 1][pos];
					int diffUnder = Math.abs(valUnder - valHere);
					ret = Math.min(ret, diffUnder + getMin(rowIdx, (rowUsed | (1 << pos)), (nextRowUsed | (1 << pos))));
				}

				// 오른쪽 칸과 묶을 수 있는 경우
				if (pos + 1 < 3 && ((rowUsed & (1 << (pos + 1))) == 0)) {
					int valRight = grid[rowIdx][pos + 1];
					int diffRight = Math.abs(valRight - valHere);
					ret = Math.min(ret,
							diffRight + getMin(rowIdx, (rowUsed | (1 << pos) | (1 << (pos + 1))), nextRowUsed));
				}
			}
		}

		return minCache[rowIdx][rowUsed][nextRowUsed] = ret;
	}

}