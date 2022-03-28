// 15384KB, 132ms

package bj4963;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 50, MAX_W = 50;
	static final int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static final int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };

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

		public Position getNextPos(int dir) {
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Position(nextY, nextX);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}
			return false;
		}

		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		public void visit() {
			isVisited[this.y][this.x] = true;
		}
		
		public boolean isLand() {
			return (grid[this.y][this.x] == 1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while (true) {
			st = new StringTokenizer(br.readLine(), " ");
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			if (W == 0 && H == 0) {
				break;
			}
			
			for (int y = 0; y < H; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < W; x++) {
					grid[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			int answer = countIslands();
			
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	/** 현재 grid에서의 섬의 수를 리턴 */
	public static int countIslands() {
		initIsVisited();
		int ret = 0;
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				Position startPos = new Position(y, x);
				if (!startPos.isVisited() && startPos.isLand()) {
					bfs(startPos);
					ret++;
				}
			}
		}

		return ret;
	}

	public static void bfs(Position startPos) {
		Queue<Position> q = new LinkedList<>();

		startPos.visit();
		q.offer(startPos);

		while (!q.isEmpty()) {
			Position here = q.poll();

			for (int dir = 0; dir < dy.length; dir++) {
				Position there = here.getNextPos(dir);
				if (there.isInRange() && !there.isVisited() && there.isLand()) {
					there.visit();
					q.offer(there);
				}
			}

		}

	}

	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}

}