// 24012KB, 496ms

package bj17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/** R, C, M의 최댓값 */
	static final int MAX_R = 100, MAX_C = 100, MAX_M = MAX_R * MAX_C;
	/** 각 방향을 나타내는 상수 */
	static final int UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	/** 각 방향에 대한 r 변화량 (방향 상수와 인덱스를 일치시키기 위해서 0번 인덱스에는 의미없는 값 추가) */
	static final int[] dr = { 0, -1, 1, 0, 0 };
	/** 각 방향에 대한 c 변화량 (방향 상수와 인덱스를 일치시키기 위해서 0번 인덱스에는 의미없는 값 추가) */
	static final int[] dc = { 0, 0, 0, 1, -1 }; //

	/** 그리드 세로 크기, 가로 크기, 초기 상어 수 */
	static int R, C, M;
	/** 그리드 */
	static Shark[][] grid;

	/** 상어 객체 */
	static class Shark {
		int r;
		int c;
		int speed;
		int direction;
		int size;

		/** 생성자 */
		public Shark(int r, int c, int speed, int direction, int size) {
			super();
			this.r = r;
			this.c = c;
			// 유효 speed만큼만 저장
			if (direction == UP || direction == DOWN) {
				this.speed = speed % (2 * (R - 1));
			}
			else {
				this.speed = speed % (2 * (C - 1));
			}
			this.direction = direction;
			this.size = size;
		}

		/** 상어를 이동시키고, r, c, direction 값을 변경시킨다 */
		public void move() {
			// speed만큼 이동
			for (int i = 0; i < this.speed; i++) {
				// 다음 위치 계산
				int nr = this.r + dr[this.direction];
				int nc = this.c + dc[this.direction];

				// 이동 가능하면 그대로 이동
				if (isInRange(nr, nc)) {
					this.r = nr;
					this.c = nc;
				}

				// 범위 밖이면 이동 방향을 반대로 바꿔서 이동
				else {
					this.direction = getOppositeDirection(this.direction);
					nr = this.r + dr[this.direction];
					nc = this.c + dc[this.direction];
					this.r = nr;
					this.c = nc;
				}
			}
		}


	}

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// R, C, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 상어 초기 상태 입력
		grid = new Shark[R + 1][C + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int speed = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
			int size = Integer.parseInt(st.nextToken());

			grid[r][c] = new Shark(r, c, speed, direction, size);
		}

		int answer = 0;
		// 1. 낚시왕 이동
		for (int fisherMan = 1; fisherMan <= C; fisherMan++) {
			// 2. 낚시
			answer += catchClosestShark(fisherMan);

			// 3. 상어 이동
			moveSharks();
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** 상어들이 이동하고 grid의 상태를 갱신 */
	public static void moveSharks() {
		Shark[][] updatedGrid = new Shark[R + 1][C + 1];

		for (int r = 1; r <= R; r++) {
			for (int c = 1; c <= C; c++) {
				// 칸에 상어가 있는 경우
				if (grid[r][c] != null) {
					// 상어 이동
					Shark shark = grid[r][c];
					shark.move();

					// 이동한 칸이 빈 칸이거나 이 상어보다 작은 상어가 들어 있다면, 이 상어를 넣는다
					if (updatedGrid[shark.r][shark.c] == null || updatedGrid[shark.r][shark.c].size < shark.size) {
						updatedGrid[shark.r][shark.c] = shark;
					}
				}
			}
		}

		grid = updatedGrid;
	}

	/** c열에서 땅에 가장 가까운 상어를 잡아서 grid를 갱신하고, 그 크기를 리턴 */
	public static int catchClosestShark(int c) {
		int ret = 0;

		for (int r = 1; r <= R; r++) {
			if (grid[r][c] != null) {
				ret = grid[r][c].size;
				grid[r][c] = null;
				break;
			}
		}

		return ret;
	}

	/** 파라미터로 주어진 방향의 반대 방향을 리턴 */
	public static int getOppositeDirection(int direction) {
		switch (direction) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		default:
			return -1;
		}
	}

	/** (r, c)가 grid 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int r, int c) {
		if (1 <= r && r <= R && 1 <= c && c <= C) {
			return true;
		}

		return false;
	}
}