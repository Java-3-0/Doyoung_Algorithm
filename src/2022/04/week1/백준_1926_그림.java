// 61072KB, 408ms

package bj1926;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_H = 500, MAX_W = 500;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W;
	static int[][] grid = new int[MAX_H][MAX_W];
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}

			return false;
		}

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

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

		int cnt = 0;
		int maxArea = 0;

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (!isVisited[y][x] && grid[y][x] == 1) {
					maxArea = Math.max(maxArea, bfs(new Position(y, x)));
					cnt++;
				}
			}
		}
		
		System.out.println(cnt);
		if (cnt == 0) {
			System.out.println(0);
		}
		else {
			System.out.println(maxArea);
		}

	} // end main

	public static int bfs(Position start) {
		Queue<Position> q = new LinkedList<>();

		isVisited[start.y][start.x] = true;
		q.offer(start);

		int ret = 0;
		while (!q.isEmpty()) {
			Position now = q.poll();
			ret++;
			for (int dir = 0; dir < dy.length; dir++) {
				Position next = now.getNextPos(dir);
				if (next.isInRange() && !isVisited[next.y][next.x] && grid[next.y][next.x] == 1) {
					isVisited[next.y][next.x] = true;
					q.offer(next);
				}
			}
		}

		return ret;
	}

}