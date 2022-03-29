// 68540KB, 296ms

package bj18404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500;
	static final int MAX_M = 1000;
	static final int INF = 987654321;
	static final int[] dy = { -2, -2, -1, -1, 1, 1, 2, 2 };
	static final int[] dx = { -1, 1, -2, 2, -2, 2, -1, 1 };

	static int N, M;
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[][] dists = new int[MAX_N + 1][MAX_N + 1];
	static boolean[][] isVisited = new boolean[MAX_N + 1][MAX_N + 1];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public boolean isInRange() {
			if (1 <= this.y && this.y <= N && 1 <= this.x && this.x <= N) {
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

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public void setDist(int dist) {
			dists[this.y][this.x] = dist;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 나이트 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		int startX = Integer.parseInt(st.nextToken());
		int startY = Integer.parseInt(st.nextToken());
		Position start = new Position(startY, startX);

		// 나이트 위치로부터 bfs 수행하면서 다른 모든 위치로의 거리 계산
		bfs(start);

		// 쿼리 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int targetX = Integer.parseInt(st.nextToken());
			int targetY = Integer.parseInt(st.nextToken());
			
			int answer = dists[targetY][targetX];
			sb.append(answer).append(" ");
		}
		sb.append("\n");
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	public static void bfs(Position start) {
		for (int i = 0; i < dists.length; i++) {
			Arrays.fill(dists[i], INF);
		}

		Queue<Position> q = new LinkedList<>();
		start.visit();
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				here.setDist(depth);

				for (int dir = 0; dir < dy.length; dir++) {
					Position there = here.getNextPos(dir);
					if (there.isInRange() && !there.isVisited()) {
						there.visit();
						q.offer(there);
					}
				}
			}

			depth++;
		}

	}

}