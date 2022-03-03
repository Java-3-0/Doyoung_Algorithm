// 289580KB, 496ms

package bj14502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언

	/** 그리드 최대 크기 */
	static final int MAX_HEIGHT = 8, MAX_WIDTH = 8;
	/** 사방탐색 방향 수 */
	static final int DIRECTIONS = 4;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { 0, 0, -1, 1 };

	// 전역변수 선언

	/** 그리드 크기 */
	static int height, width;
	/** 그리드 */
	static int[][] grid = new int[MAX_HEIGHT][MAX_WIDTH];

	// 객체 선언

	/** 위치 객체 */
	static class Position {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;

		/** y좌표, x좌표를 받아서 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 위치가 그리드 범위 내에 들어가면 true, 아니면 false를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < height && 0 <= this.x && this.x < width) {
				return true;
			}
			return false;
		}

		/** 위치에서 dir 방향으로 한 칸 이동한 다음 위치를 리턴 */
		public Position getNextPosition(int dir) {
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Position(nextY, nextX);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());

		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int maxArea = 0;
		
		// 모든 벽 3개 쌍에 대해 완전탐색
		for (int i = 0; i < height * width; i++) {
			for (int j = i + 1; j < height * width; j++) {
				for (int k = j + 1; k < height * width; k++) {
					int iy = i / width;
					int ix = i % width;
					int jy = j / width;
					int jx = j % width;
					int ky = k / width;
					int kx = k % width;

					Position a = new Position(iy, ix);
					Position b = new Position(jy, jx);
					Position c = new Position(ky, kx);
		
					// 벽 3개를 지은 상태에서의 안전 영역의 수를 센다
					int area = getSafeArea(a, b, c);

					// 최대치 갱신
					maxArea = maxArea < area ? area : maxArea;
				}
			}
		}

		// 출력
		System.out.println(maxArea);

	} // end main

	public static int getSafeArea(Position a, Position b, Position c) {
		// 셋 중 하나라도 벽을 세울 수 없는 칸이면 실패
		if (grid[a.y][a.x] != 0  || grid[b.y][b.x] != 0 || grid[c.y][c.x] != 0) {
			return -1;
		}
		
		// grid 자체를 갱신하지 않을 것이니 일단 복사
		int[][] copy = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				copy[y][x] = grid[y][x];
			}
		}
		
		// 벽 3개 세우기
		copy[a.y][a.x] = 1;
		copy[b.y][b.x] = 1;
		copy[c.y][c.x] = 1;
		
		// 바이러스의 초기 위치들을 큐에 넣는다
		Queue<Position> q = new LinkedList<>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (copy[y][x] == 2) {
					q.offer(new Position(y, x));
				}
			}
		}

		// bfs를 통해 바이러스가 확산된다
		while (!q.isEmpty()) {
			Position here = q.poll();

			for (int dir = 0; dir < 4; dir++) {
				Position there = here.getNextPosition(dir);
				if (there.isInRange() && copy[there.y][there.x] == 0) {
					copy[there.y][there.x] = 2;
					q.offer(there);
				}
			}
		}

		// 남은 안전 영역의 수를 센다
		int ret = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (copy[y][x] == 0) {
					ret++;
				}
			}
		}

		return ret;
	}
}