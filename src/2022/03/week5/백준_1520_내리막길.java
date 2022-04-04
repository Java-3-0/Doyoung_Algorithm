// 40476KB, 320ms

package bj1520;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 500, MAX_W = 500;
	static final int CACHE_EMPTY = -1;
	static final int[] dy = {-1, 1, 0, 0};
	static final int[] dx = {0, 0, -1, 1};

	static int H, W;
	static int[][] grid = new int[MAX_H][MAX_W];
	static long[][] cache = new long[MAX_H][MAX_W];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 정답 계산
		long answer = getNumberOfWays(0, 0);

		// 출력
		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static long getNumberOfWays(int startY, int startX) {
		// base case
		if (startY == H - 1 && startX == W - 1) {
			return 1L;
		}

		if (cache[startY][startX] != CACHE_EMPTY) {
			return cache[startY][startX];
		}

		long ret = 0L;
		for (int dir = 0; dir < dy.length; dir++) {
			int nextY = startY + dy[dir];
			int nextX = startX + dx[dir];
			if (isInRange(nextY, nextX) && grid[nextY][nextX] < grid[startY][startX]) {
				ret += getNumberOfWays(nextY, nextX);
			}
		}
		
		return cache[startY][startX] = ret;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}

		return false;
	}

}