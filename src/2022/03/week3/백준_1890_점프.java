// 11924KB, 92ms

package bj1890;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final long CACHE_EMPTY = -1L;

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];
	static long[][] cache = new long[MAX_N + 1][MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();
		
		// 입력
		N = Integer.parseInt(br.readLine());
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 정답 계산
		long answer = countPaths(0, 0);

		// 출력
		System.out.println(answer);
	}

	public static long countPaths(int startY, int startX) {
		// base case : 도착한 경우
		if (startY == N - 1 && startX == N - 1) {
			return 1L;
		}

		// 캐시에 이미 저장되어 있는 경우
		if (cache[startY][startX] != CACHE_EMPTY) {
			return cache[startY][startX];
		}

		// 새로 계산해서 캐시에 저장하고 리턴하는 경우
		long ret = 0L;
		int move = grid[startY][startX];
		if (move == 0) {
			ret = 0L;
		} else {
			int nextY = startY + move;
			int nextX = startX + move;

			if (isInRange(nextY, startX)) {
				ret += countPaths(nextY, startX);
			}
			if (isInRange(startY, nextX)) {
				ret += countPaths(startY, nextX);
			}
		}

		return cache[startY][startX] = ret;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < N && 0 <= x && x < N) {
			return true;
		}
		return false;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		int len = cache.length;
		for (int i = 0; i < len; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}
