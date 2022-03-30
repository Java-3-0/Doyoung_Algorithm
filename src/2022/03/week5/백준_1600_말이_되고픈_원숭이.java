// 300040KB, 668ms

package bj1600;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_W = 200, MAX_H = 200, MAX_K = 30;
	static final int[] dyJump = { -2, -2, -1, -1, 1, 1, 2, 2 };
	static final int[] dxJump = { -1, 1, -2, 2, -2, 2, -1, 1 };
	static final int[] dyWalk = { 0, 0, -1, 1 };
	static final int[] dxWalk = { -1, 1, 0, 0 };

	static int W, H, K;
	static int[][] grid = new int[MAX_H][MAX_W];

	static class Status {
		int y;
		int x;
		int jumps;

		public Status(int y, int x, int jumps) {
			super();
			this.y = y;
			this.x = x;
			this.jumps = jumps;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// K 입력
		K = Integer.parseInt(br.readLine());

		// W, H 입력
		st = new StringTokenizer(br.readLine(), " ");
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 정답 계산
		int answer = getMinMoves();

		// 출력
		System.out.println(answer);

	} // end main

	/** 좌상단에서 우하단으로 갈 수 있는 최소 횟수 */
	public static int getMinMoves() {

		Queue<Status> q = new LinkedList<>();
		boolean[][][] isVisited = new boolean[H][W][K + 1];

		q.offer(new Status(0, 0, 0));
		isVisited[0][0][0] = true;

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Status now = q.poll();

				// 도착
				if (now.y == H - 1 && now.x == W - 1) {
					return depth;
				}

				// 점프 횟수 남은 경우 점프 시도
				if (now.jumps < K) {
					for (int dir = 0; dir < dyJump.length; dir++) {
						Status next = new Status(now.y + dyJump[dir], now.x + dxJump[dir], now.jumps + 1);
						if (isInRange(next.y, next.x) && !isVisited[next.y][next.x][next.jumps]
								&& grid[next.y][next.x] == 0) {
							isVisited[next.y][next.x][next.jumps] = true;
							q.offer(next);
						}
					}
				}

				// 걷기 시도
				for (int dir = 0; dir < dyWalk.length; dir++) {
					Status next = new Status(now.y + dyWalk[dir], now.x + dxWalk[dir], now.jumps);
					if (isInRange(next.y, next.x) && !isVisited[next.y][next.x][next.jumps]
							&& grid[next.y][next.x] == 0) {
						isVisited[next.y][next.x][next.jumps] = true;
						q.offer(next);
					}
				}

			} // end for i

			depth++;

		} // end while

		return -1;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}
}