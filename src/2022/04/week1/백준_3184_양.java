// 24656KB, 180ms

package bj3184;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 250;
	static final int MAX_W = 250;
	static final char EMPTY = '.', WALL = '#', SHEEP = 'o', WOLF = 'v';
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
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

		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		public void visit() {
			isVisited[this.y][this.x] = true;
		}
		
		public char getGridVal() {
			return grid[this.y][this.x];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// grid 입력
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		int sheeps = 0;
		int wolves = 0;
		
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (!isVisited[y][x]) {
					int cnt = bfs(new Position(y, x));
					if (cnt >= 0) {
						sheeps += cnt;
					}
					else {
						wolves += (-cnt);
					}
				}
			}
		}
		
		System.out.println(sheeps + " " + wolves);

	} // end main

	/** start 지점부터 bfs를 해서 양이 많으면 양의 수를 양수로, 늑대가 많으면 늑대의 수를 음수로 리턴 */
	public static int bfs(Position start) {
		Queue<Position> q = new LinkedList<>();
		start.visit();
		q.offer(start);
		
		int sheepCnt = 0;
		int wolfCnt = 0;
		
		while (!q.isEmpty()) {
			Position now = q.poll();
			char val = now.getGridVal();
			if (val == SHEEP) {
				sheepCnt++;
			}
			else if (val == WOLF) {
				wolfCnt++;
			}
			
			for (int dir = 0; dir < dy.length; dir++) {
				Position next = now.getNextPos(dir);
				if (next.isInRange() && !next.isVisited() && next.getGridVal() != WALL) {
					next.visit();
					q.offer(next);
				}
			}
			
		}
		
		if (wolfCnt < sheepCnt) {
			return sheepCnt;
		}
		else {
			return -wolfCnt;
		}
	}

}