package bj1303;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int width, height;
	static char[][] grid;
	static boolean[][] isVisited;

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public Position getNextPosition(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < height && 0 <= this.x && this.x < width) {
				return true;
			}

			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 그리드 너비, 높이 입력
		st = new StringTokenizer(br.readLine(), " ");
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());

		// 메모리 할당
		grid = new char[height][width];
		isVisited = new boolean[height][width];
		
		// 그리드 입력
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		// 각 나라의 파워 계산
		int powerW = 0;
		int powerB = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!isVisited[y][x]) {
					// 파워 계산
					int cnt = bfs(new Position(y, x));
					int power = cnt * cnt;
					
					// 해당 나라에 파워 추가
					if (grid[y][x] == 'W') {
						powerW += power;
					}
					else {
						powerB += power;
					}
				}
			}
		}
		
		System.out.printf("%d %d\n", powerW, powerB);
	}

	/** (startY, startX) 로부터 bfs를 돌면서 방문한 정점 수를 리턴 */
	public static int bfs(Position start) {
		int ret = 0;
		char nation = grid[start.y][start.x];
		Queue<Position> q = new LinkedList<>();
		isVisited[start.y][start.x] = true;
		q.add(start);
		
		while(!q.isEmpty()) {
			Position now = q.poll();
			ret++;
			
			for (int dir = 0; dir < DIRECTIONS; dir++) {
				Position next = now.getNextPosition(dir);
				if (next.isInRange() && !isVisited[next.y][next.x] && grid[next.y][next.x] == nation) {
					isVisited[next.y][next.x] = true;
					q.offer(next);	
				}
			}
		}

		return ret;
	}

}
