// 76504KB, 512ms

package bj11048;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000, MAX_M = 1000;
	static final int CACHE_EMPTY = -1;
	static final int FAIL = -987654321;

	static final int[] dy = { 0, 1, 1 };
	static final int[] dx = { 1, 0, 1 };

	static int N, M;
	static int[][] grid = new int[MAX_N + 1][MAX_M + 1];
	static int[][] cache = new int[MAX_N + 1][MAX_M + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= M; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int answer = getMaxCandies(1, 1);

		System.out.println(answer);

	} // end main

	/** (startY, startX) 위치부터 (N, M)까지 가면서 먹을 수 있는 사탕 개수의 최대값을 리턴 */
	public static int getMaxCandies(int startY, int startX) {
		// 도착한 경우 그 칸의 사탕을 먹고 종료
		if (startY == N && startX == M) {
			return grid[startY][startX];
		}

		// 그리드 범위를 초과한 경우 실패
		if (!isInRange(startY, startX)) {
			return FAIL;
		}

		// 캐시에 이미 저장되어 있는 경우
		if (cache[startY][startX] != CACHE_EMPTY) {
			return cache[startY][startX];
		}

		// 새로 계산해서 캐시에 넣는 경우
		int candyHere = grid[startY][startX];
		int candyFuture = FAIL;
		for (int dir = 0; dir < dy.length; dir++) {
			int nextY = startY + dy[dir];
			int nextX = startX + dx[dir];
			candyFuture = Math.max(candyFuture, getMaxCandies(nextY, nextX));
		}

		return cache[startY][startX] = candyHere + candyFuture;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= M) {
			return true;
		}

		return false;
	}
}