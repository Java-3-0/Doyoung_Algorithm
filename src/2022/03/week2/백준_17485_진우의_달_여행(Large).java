// 114124KB, 784ms

package bj17484;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 1000;
	static final int MAX_W = 1000;
	static final int CACHE_EMPTY = -1;
	static final int[] dx = { -1, 0, 1 };
	static final int INF = 987654321;

	static int H, W;
	static int[][] grid = new int[MAX_H][MAX_W];
	static int[][][] cache = new int[MAX_H][MAX_W][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 최소 비용 계산
		int answer = solve();

		// 출력
		System.out.println(answer);
	}

	/** 최소 비용을 계산해서 리턴하는 함수 */
	public static int solve() {
		int minCost = INF;
		for (int startX = 0; startX < W; startX++) {
			for (int dir = 0; dir < 3; dir++) {
				int nextY = 1;
				int nextX = startX + dx[dir];
				if (isInRange(nextY, nextX)) {
					int cost = grid[0][startX] + getMinCost(nextY, nextX, dir);
					minCost = Math.min(minCost, cost);
				}
			}
		}

		return minCost;
	}

	/** 이전에 이동한 방향이 lastDir일 때, (nowY, nowX) 위치부터 내려가는 최소 비용을 리턴 */
	public static int getMinCost(int nowY, int nowX, int lastDir) {
		if (nowY == H - 1) {
			return grid[nowY][nowX];
		}

		if (cache[nowY][nowX][lastDir] != CACHE_EMPTY) {
			return cache[nowY][nowX][lastDir];
		}

		int minCost = INF;
		for (int dir = 0; dir < 3; dir++) {
			// 같은 방향으로 두 번 이동할 수는 없다.
			if (dir == lastDir) {
				continue;
			}

			// 다음 위치에서 재귀 호출
			int nextY = nowY + 1;
			int nextX = nowX + dx[dir];
			if (isInRange(nextY, nextX)) {
				minCost = Math.min(minCost, getMinCost(nextY, nextX, dir));
			}
		}

		return cache[nowY][nowX][lastDir] = grid[nowY][nowX] + minCost;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}

		return false;
	}

	/** 캐시 초기화 함수 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}
}