// 20620KB, 204ms

package bj14430;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300, MAX_M = 300;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, M;;
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
		
		int answer = solve(1, 1);
		
		System.out.println(answer);
		

	} // end main
	
	public static int solve(int startY, int startX) {
		if (!isInRange(startY, startX)) {
			return -INF;
		}
		
		if (startY == N && startX == M) {
			return grid[startY][startX];
		}
		
		if (cache[startY][startX] != CACHE_EMPTY) {
			return cache[startY][startX];
		}
		
		int here = grid[startY][startX];
		int ret = Math.max(here + solve(startY + 1, startX), here + solve(startY, startX + 1));
		
		return cache[startY][startX] = ret;
	}
	
	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= M) {
			return true;
		}
		
		return false;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}