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
	
	static final int[] dy = {0, 1, 1};
	static final int[] dx = {1, 0, 1};
	
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
		
		int answer =getMaxCandies(1, 1);
		
		System.out.println(answer);

	} // end main
	
	public static int getMaxCandies(int startY, int startX) {
		if (startY == N && startX == M) {
			return grid[startY][startX];
		}
		
		if (!isInRange(startY, startX)) {
			return FAIL;
		}
		
		if (cache[startY][startX] != CACHE_EMPTY) {
			return cache[startY][startX];
		}
		
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