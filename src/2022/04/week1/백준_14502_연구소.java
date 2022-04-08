// 270280KB, 392ms

package bj14502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언

	/** 그리드 최대 크기 */
	static final int MAX_H = 8, MAX_W = 8;
	/** 사방탐색 방향 수 */
	static final int DIRECTIONS = 4;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { 0, 0, -1, 1 };
	/** 그리드의 각 칸의 상태를 나타내는 상수 */
	static final int EMPTY = 0, WALL = 1, VIRUS = 2;

	// 전역변수 선언

	/** 그리드 크기 */
	static int H, W;
	/** 그리드 */
	static int[][] grid = new int[MAX_H][MAX_W];;
	/** 바이러스의 위치들 */
	static List<Position> viruses = new ArrayList<>();
	/** 초기 상태에서 빈 칸의 수 */
	static int cntEmpty = 0;
	/** 방문 여부 */
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];

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
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}
			return false;
		}

		/** 위치가 방문되었는지 여부를 리턴 */
		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		/** 위치를 방문 */
		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		/** 위치에서 dir 방향으로 한 칸 이동한 다음 위치를 리턴 */
		public Position getNextPosition(int dir) {
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Position(nextY, nextX);
		}

		/** 그리드에서 이 위치에 있는 값을 리턴 */
		public int getGridVal() {
			return grid[this.y][this.x];
		}

		/** 그리드에서 이 위치에 값을 넣는다 */
		public void setGridVal(int val) {
			grid[this.y][this.x] = val;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 그리드 입력받고, 바이러스의 위치들은 따로 리스트로 저장, 빈 칸의 수는 카운트
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				int input = Integer.parseInt(st.nextToken());
				grid[y][x] = input;

				if (input == VIRUS) {
					viruses.add(new Position(y, x));
				} else if (input == EMPTY) {
					cntEmpty++;
				}
			}
		}

		int maxSafeArea = 0;

		// 모든 벽 3개 쌍에 대해 완전탐색
		for (int i = 0; i < H * W; i++) {
			Position a = new Position(i / W, i % W);
			if (a.getGridVal() != EMPTY) {
				continue;
			}

			for (int j = i + 1; j < H * W; j++) {
				Position b = new Position(j / W, j % W);
				if (b.getGridVal() != EMPTY) {
					continue;
				}

				for (int k = j + 1; k < H * W; k++) {
					Position c = new Position(k / W, k % W);
					if (c.getGridVal() != EMPTY) {
						continue;
					}

					// 벽 짓기
					a.setGridVal(WALL);
					b.setGridVal(WALL);
					c.setGridVal(WALL);

					// 벽 3개를 지은 상태에서의 안전 영역의 수를 센다
					int area = getSafeArea();

					// 최대치 갱신
					maxSafeArea = maxSafeArea < area ? area : maxSafeArea;

					// 지은 벽 되돌리기
					a.setGridVal(EMPTY);
					b.setGridVal(EMPTY);
					c.setGridVal(EMPTY);
				}
			}
		}

		// 출력
		System.out.println(maxSafeArea);

	} // end main

	/** 현재 grid 상태에서 바이러스가 확산되었을 때의 안전 영역 수를 계산해서 리턴 */
	public static int getSafeArea() {
		// 방문 여부 false로 초기화
		initIsVisited();

		// 바이러스의 초기 위치들을 큐에 넣는다
		Queue<Position> q = new LinkedList<>();
		for (Position pos : viruses) {
			pos.visit();
			q.offer(pos);
		}

		// bfs로 방문 가능한 empty 칸의 수를 카운트할 변수
		int cntVisit = -q.size();

		// bfs를 통해 바이러스가 확산된다
		while (!q.isEmpty()) {
			Position now = q.poll();
			cntVisit++;

			for (int dir = 0; dir < DIRECTIONS; dir++) {
				Position next = now.getNextPosition(dir);
				if (next.isInRange() && next.getGridVal() == EMPTY && !next.isVisited()) {
					next.visit();
					q.offer(next);
				}
			}
		}

		// 남은 안전 영역의 수를 센다 (전체 빈 칸 수 - 방문한 빈 칸 수 - 벽이 세워진 빈 칸 수)
		int ret = cntEmpty - cntVisit - 3;

		return ret;
	}

	/** isVisited[] 배열을 false로 초기화 */
	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}