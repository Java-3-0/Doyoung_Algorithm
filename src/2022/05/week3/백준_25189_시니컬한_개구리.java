// 184440KB, 828ms

package bj25189;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 1000, MAX_W = 1000;
	static final int CACHE_EMPTY = -1;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final int INF = 987654321;

	static int H, W;
	static int startY, startX, targetY, targetX;
	static int[][] grid = new int[MAX_H + 1][MAX_W + 1];
	static boolean[][][] isVisited = new boolean[MAX_H + 1][MAX_W + 1][2];
	static int[][][] cache = new int[MAX_H + 1][MAX_W + 1][2];
	static boolean[] isTriedRow = new boolean[MAX_H + 1];
	static boolean[] isTriedCol = new boolean[MAX_W + 1];

	static class Status {
		int y;
		int x;
		int usedFreeJump;

		public Status(int y, int x, int usedFreeJump) {
			super();
			this.y = y;
			this.x = x;
			this.usedFreeJump = usedFreeJump;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 시작 정점과 끝 정점의 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		startY = Integer.parseInt(st.nextToken());
		startX = Integer.parseInt(st.nextToken());
		targetY = Integer.parseInt(st.nextToken());
		targetX = Integer.parseInt(st.nextToken());

		// 그리드 정보 입력
		for (int y = 1; y <= H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 최소 점프 수 계산
		int answer = getMinJumps();

		// 출력
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	/** (startY, startX) 에서 (targetY, targetX)로의 최소 점프 수를 bfs로 구해서 리턴 */
	public static int getMinJumps() {
		Queue<Status> q = new ArrayDeque<>();

		isVisited[startY][startX][0] = true;
		q.offer(new Status(startY, startX, 0));
		isVisited[startY][startX][1] = true;
		q.offer(new Status(startY, startX, 1));

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Status now = q.poll();

				if (now.y == targetY && now.x == targetX) {
					return depth;
				}

				// 그리드에 적힌 대로 점프 시도
				for (int dir = 0; dir < dy.length; dir++) {
					int jumpAmount = grid[now.y][now.x];
					int nextY = now.y + jumpAmount * dy[dir];
					int nextX = now.x + jumpAmount * dx[dir];
					int nextUsedFreeJump = now.usedFreeJump;
					if (isInRange(nextY, nextX) && !isVisited[nextY][nextX][nextUsedFreeJump]) {
						isVisited[nextY][nextX][nextUsedFreeJump] = true;
						q.offer(new Status(nextY, nextX, nextUsedFreeJump));
					}
				}

				// 프리 점프를 아직 쓰지 않았다면 쓰는 것을 시도
				if (now.usedFreeJump == 0) {
					// 같은 row에서 가로로 프리 점프
					if (!isTriedRow[now.y]) {
						isTriedRow[now.y] = true;
						for (int nextX = 1; nextX <= W; nextX++) {
							int nextY = now.y;
							int nextUsedFreeJump = 1;
							if (!isVisited[nextY][nextX][1]) {
								isVisited[nextY][nextX][1] = true;
								q.offer(new Status(nextY, nextX, nextUsedFreeJump));
							}
						}
					}

					// 같은 col에서 세로로 프리 점프
					if (!isTriedCol[now.x]) {
						isTriedCol[now.x] = true;
						for (int nextY = 1; nextY <= H; nextY++) {
							int nextX = now.x;
							if (!isVisited[nextY][nextX][1]) {
								isVisited[nextY][nextX][1] = true;
								q.offer(new Status(nextY, nextX, 1));
							}
						}
					}
				}
			}

			depth++;
		}

		return -1;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= H && 1 <= x && x <= W) {
			return true;
		}
		return false;
	}
}