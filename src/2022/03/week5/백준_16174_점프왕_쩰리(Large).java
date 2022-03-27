// 12440KB, 96ms

package bj16174;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 64;

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
				return true;
			}
			return false;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		String answer = canArrive() ? "HaruHaru" : "Hing";

		System.out.println(answer);

	} // end main

	public static boolean canArrive() {
		Queue<Position> q = new LinkedList<>();
		boolean[][] isVisited = new boolean[N][N];

		Position start = new Position(0, 0);
		isVisited[start.y][start.x] = true;
		q.offer(start);

		while (!q.isEmpty()) {
			Position here = q.poll();
			int move = grid[here.y][here.x];
			if (move == -1) {
				return true;
			}

			int nextX = here.x + move;
			int nextY = here.y + move;

			Position next1 = new Position(nextY, here.x);
			Position next2 = new Position(here.y, nextX);

			if (next1.isInRange() && !isVisited[next1.y][next1.x]) {
				isVisited[next1.y][next1.x] = true;
				q.offer(next1);
			}

			if (next2.isInRange() && !isVisited[next2.y][next2.x]) {
				isVisited[next2.y][next2.x] = true;
				q.offer(next2);
			}
		}

		return false;
	}

}