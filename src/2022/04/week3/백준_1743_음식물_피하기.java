// 13580KB, 120ms

package bj1743;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100, MAX_W = 100;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W, K;
	static boolean[][] isTrash = new boolean[MAX_H + 1][MAX_W + 1];
	static boolean[][] isVisited = new boolean[MAX_H + 1][MAX_W + 1];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public Position getNextPos(int dir) {
			int ny = this.y + dy[dir];
			int nx = this.x + dx[dir];
			return new Position(ny, nx);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			isTrash[r][c] = true;
		}

		int maxCnt = 0;
		for (int y = 1; y <= H; y++) {
			for (int x = 1; x <= W; x++) {
				if (!isVisited[y][x] && isTrash[y][x]) {
					int cnt = bfs(new Position(y, x));
					maxCnt = maxCnt < cnt ? cnt : maxCnt;
				}
			}
		}
		
		System.out.println(maxCnt);

	} // end main

	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= H && 1 <= x && x <= W) {
			return true;
		}

		return false;
	}

	/** bfs로 depth 2까지의 동기 수를 리턴 */
	public static int bfs(Position start) {
		Queue<Position> q = new ArrayDeque<>();

		isVisited[start.y][start.x] = true;
		q.offer(start);

		int cnt = 0;
		while (!q.isEmpty()) {
			Position now = q.poll();
			cnt++;

			for (int dir = 0; dir < 4; dir++) {
				Position next = now.getNextPos(dir);
				if (isInRange(next.y, next.x) && !isVisited[next.y][next.x] && isTrash[next.y][next.x]) {
					isVisited[next.y][next.x] = true;
					q.offer(next);
				}

			}

		} // end while

		return cnt;
	}

}