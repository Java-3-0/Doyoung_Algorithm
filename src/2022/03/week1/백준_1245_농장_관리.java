// 14688KB, 116ms

package bj1245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100;
	static final int MAX_W = 70;

	static final int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static final int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };

	static int[][] grid = new int[MAX_H][MAX_W];
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];
	static int H, W;

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

		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public int getHeight() {
			return grid[this.y][this.x];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 높이, 너비 입력
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

		int answer = countMountains();

		System.out.println(answer);
	} // end main

	/** 산봉우리의 개수를 세서 리턴 */
	public static int countMountains() {
		int ret = 0;

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (!isVisited[y][x]) {
					ret += bfs(new Position(y, x));
				}
			}
		}

		return ret;
	}

	/** bfs로 자신과 높이가 같은 곳을 모두 방문한다. 인접한 곳 중 자신보다 높은 곳이 있다면 1, 아니면 0을 리턴 */
	public static int bfs(Position start) {
		Queue<Position> q = new LinkedList<>();

		// 시작 지점 처리
		start.visit();
		q.offer(start);

		boolean isMountain = true;
		
		// bfs 수행
		while (!q.isEmpty()) {
			Position here = q.poll();

			for (int dir = 0; dir < 8; dir++) {
				Position there = here.getNextPos(dir);
				if (there.isInRange()) {
					int thereH = there.getHeight();
					int hereH = here.getHeight();

					if (thereH == hereH) {
						if (!there.isVisited()) {
							there.visit();
							q.offer(there);
						}
					}

					if (thereH > hereH) {
						isMountain = false;
					}
				}
			}
		}

		if (isMountain) {
			return 1;
		}
		else {
			return 0;
		}
	}
}