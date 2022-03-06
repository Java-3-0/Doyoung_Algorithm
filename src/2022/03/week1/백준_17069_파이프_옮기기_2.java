// 12380KB, 92ms

package bj17069;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	/** 그리드 최대 크기 */
	static final int MAX_N = 32;
	/** 파이프 방향을 나타내는 상수 */
	static final int HORIZONTAL = 0, VERTICAL = 1, DIAGONAL = 2;
	/** 빈 캐시를 나타내는 상수 */
	static final long CACHE_EMPTY = -1L;

	/** 그리드 크기 */
	static int N;
	/** 그리드 */
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	/** 캐시 */
	static long[][][] cache = new long[MAX_N + 1][MAX_N + 1][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 캐시 초기화
		initCache();

		// 그리드 입력
		N = Integer.parseInt(br.readLine());
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 도달 가능한 방법 수 계산
		long answer = getNumberOfWays(1, 1, HORIZONTAL);

		// 출력
		System.out.println(answer);

	} // end main

	/**
	 * 현재 파이프의 위치와 방향이 주어졌을 때, 파이프를 (N, N)까지 이동시키는 방법의 수를 리턴
	 * 
	 * @param hereY : 현재 y 좌표
	 * @param hereX : 현재 x 좌표
	 * @param dir   : 현재 파이프 방향
	 * @return
	 */
	public static long getNumberOfWays(int hereY, int hereX, int dir) {
		// base case 1: 파이프의 칸이 맵 범위를 벗어나거나 벽과 충돌하는 등 유효하지 않은 경우
		if (!isValidPipe(hereY, hereX, dir)) {
			return 0L;
		}

		// base case 2: (N, N)에 도달한 경우
		if (isFinished(hereY, hereX, dir)) {
			return 1L;
		}

		// 캐시에 이미 저장되어 있는 경우
		if (cache[hereY][hereX][dir] != CACHE_EMPTY) {
			return cache[hereY][hereX][dir];
		}

		// 이외의 경우 새로 계산해서 캐시에 저장
		long ret = 0L;
		if (dir == HORIZONTAL) {
			ret += getNumberOfWays(hereY, hereX + 1, HORIZONTAL);
			ret += getNumberOfWays(hereY, hereX + 1, DIAGONAL);
		} else if (dir == VERTICAL) {
			ret += getNumberOfWays(hereY + 1, hereX, VERTICAL);
			ret += getNumberOfWays(hereY + 1, hereX, DIAGONAL);
		} else {
			ret += getNumberOfWays(hereY + 1, hereX + 1, HORIZONTAL);
			ret += getNumberOfWays(hereY + 1, hereX + 1, VERTICAL);
			ret += getNumberOfWays(hereY + 1, hereX + 1, DIAGONAL);
		}

		return cache[hereY][hereX][dir] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[0].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	/** 파이프가 맵 범위 내에 빈칸 위치에 있어서 유효한지 여부를 리턴 */
	public static boolean isValidPipe(int hereY, int hereX, int dir) {
		int dxMax = 0, dyMax = 0;
		if (dir == HORIZONTAL) {
			dxMax = 1;
		} else if (dir == VERTICAL) {
			dyMax = 1;
		} else {
			dyMax = 1;
			dxMax = 1;
		}

		for (int dy = 0; dy <= dyMax; dy++) {
			for (int dx = 0; dx <= dxMax; dx++) {
				if (!isEmpty(hereY + dy, hereX + dx)) {
					return false;
				}
			}
		}
		
		return true;
	}

	/** 위치가 그리드 범위 내에 있고 빈칸이면 true, 아니면 false를 리턴 */
	public static boolean isEmpty(int y, int x) {
		if (isInRange(y, x) && grid[y][x] == 0) {
			return true;
		}

		return false;
	}

	/** 위치가 그리드 범위 내에 들어가면 true, 아니면 false를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= N) {
			return true;
		}
		return false;
	}

	/** 파이프가 (N, N)에 도달했는지 여부를 리턴 */
	public static boolean isFinished(int y, int x, int dir) {
		if (y == N - 1 && x == N - 1 && dir == DIAGONAL) {
			return true;
		}
		if (y == N - 1 && x == N && dir == VERTICAL) {
			return true;
		}
		if (y == N && x == N - 1 && dir == HORIZONTAL) {
			return true;
		}
		return false;
	}
}