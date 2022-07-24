// 11968KB, 80ms

package boj3190;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100, MAX_K = 100, MAX_L = 100, MAX_X = 10000;
	static final int SNAKE = 2, APPLE = 1, EMPTY = 0;
	// 시계방향 (우, 하, 좌, 상)
	static final int DIRECTIONS = 4;
	static final int[] dy = { 0, 1, 0, -1 };
	static final int[] dx = { 1, 0, -1, 0 };

	static int N, K, L;
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[] directionChanges = new int[MAX_X + 1];

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

		public Position getNextPos(int dir) {
			return new Position(y + dy[dir], x + dx[dir]);
		}

		public boolean isInRange() {
			if (1 <= this.y && this.y <= N && 1 <= this.x && this.x <= N) {
				return true;
			}
			return false;
		}

		public int getGridVal() {
			return grid[this.y][this.x];
		}

		public void setGridVal(int val) {
			grid[this.y][this.x] = val;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		// 사과 위치 입력
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());

			grid[y][x] = APPLE;
		}

		// L 입력
		L = Integer.parseInt(br.readLine());

		// 방향 전환 정보 입력
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int time = Integer.parseInt(st.nextToken());
			char c = st.nextToken().charAt(0);
			switch (c) {
			case 'D':
				directionChanges[time] = 1;
				break;
			case 'L':
				directionChanges[time] = -1;
			default:
				break;
			}
		}

		// 초기 상태 설정
		Deque<Position> snakePositions = new ArrayDeque<>();
		Position start = new Position(1, 1);
		snakePositions.offerFirst(start);
		start.setGridVal(SNAKE);
		int dir = 0;
		int timeNow = 0;

		// 시뮬레이션
		OUTER: while (true) {
			// 시간 카운트
			timeNow++;

			// 머리 이동
			Position headPos = snakePositions.peekFirst().getNextPos(dir);
			snakePositions.offerFirst(headPos);

			// 벽을 만난 경우를 처리
			if (!headPos.isInRange()) {
				break;
			}

			// 이동한 칸의 값에 따라 처리
			int gridVal = headPos.getGridVal();
			switch (gridVal) {
			case SNAKE:
				break OUTER;
			case APPLE:
				headPos.setGridVal(SNAKE);
				break;
			case EMPTY:
				headPos.setGridVal(SNAKE);
				Position removed = snakePositions.pollLast();
				removed.setGridVal(EMPTY);
				break;
			default:
				break;
			}

			// 방향 전환
			dir = (dir + directionChanges[timeNow] + DIRECTIONS) % DIRECTIONS;
		}

		System.out.println(timeNow);

	} // end main

}