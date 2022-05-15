// 13340KB, 92ms

package bj16948;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200;
	static final int[] dy = { -2, -2, 0, 0, 2, 2 };
	static final int[] dx = { -1, 1, -2, 2, -1, 1 };

	static int N;
	static int startY, startX, endY, endX;
	static boolean[][] isVisited = new boolean[MAX_N][MAX_N];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Position [y=" + y + ", x=" + x + "]";
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
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
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");
		startY = Integer.parseInt(st.nextToken());
		startX = Integer.parseInt(st.nextToken());
		endY = Integer.parseInt(st.nextToken());
		endX = Integer.parseInt(st.nextToken());

		Position start = new Position(startY, startX);

		int answer = bfs(start);

		System.out.println(answer);

	} // end main

	private static int bfs(Position start) {
		Queue<Position> q = new ArrayDeque<>();

		isVisited[start.y][start.x] = true;
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Position now = q.poll();
				if (now.y == endY && now.x == endX) {
					return depth;
				}

				for (int dir = 0; dir < dy.length; dir++) {
					Position next = now.getNextPos(dir);
					if (next.isInRange() && !isVisited[next.y][next.x]) {
						isVisited[next.y][next.x] = true;
						q.offer(next);
					}
				}
			}

			depth++;

		}

		return -1;
	}
}