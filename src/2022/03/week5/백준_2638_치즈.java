// 72844KB, 240ms

package bj2638;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	/** 그리드 최대 크기 */
	static final int MAX_H = 100, MAX_W = 100;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { 0, 0, -1, 1 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { -1, 1, 0, 0 };
	/** 그리드 칸의 상태를 나타낼 상수 */
	static final int EMPTY = 0, CHEESE = 1;

	/** 그리드 크기 */
	static int H, W;
	/** 그리드 */
	static int[][] grid;
	/** 방문 여부 */
	static boolean[][] isVisited;

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

		public int getGridVal() {
			return grid[this.y][this.x];
		}

		public void setGridVal(int n) {
			grid[this.y][this.x] = n;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 메모리 할당
		grid = new int[H][W];
		isVisited = new boolean[H][W];

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int time = 0;

		while (true) {
			if (countCheese() == 0) {
				break;
			}

			simulate();
			time++;
		}

		// 결과 출력
		System.out.println(time);

	} // end main

	/** 한 시간을 시뮬레이션하고 남아있는 치즈 개수를 리턴 */
	public static int simulate() {
		// 방문 여부 초기화
		initIsVisited();

		// (0, 0)위치에서부터 bfs를 하면서 melt 수행
		Position start = new Position(0, 0);
		if (!start.isVisited() && start.getGridVal() == EMPTY) {
			tryMeltFrom(start);
		}

		// 남은 치즈 개수 리턴
		return countCheese();
	}

	/** start 위치부터 bfs를 하면서 만나는 치즈 칸들의 grid 값을 MELT로 변경한다 */
	public static void tryMeltFrom(Position start) {
		Queue<Position> q = new LinkedList<>();

		start.visit();
		q.offer(start);

		while (!q.isEmpty()) {
			Position now = q.poll();
			for (int dir = 0; dir < dy.length; dir++) {
				Position next = now.getNextPos(dir);
				if (next.isInRange()) {
					// 치즈를 만났을 때,
					if (next.getGridVal() == CHEESE) {
						// 두 번째로 방문하면 치즈를 녹인다.
						if (next.isVisited()) {
							next.setGridVal(EMPTY);
						} 
						// 첫 번째로 방문하면 방문 여부만 체크한다.
						else {
							next.visit();
						}
					} 
					// 빈 칸을 만났을 때,
					else {
						// 처음 방문한 칸이면 방문 체크하고 큐에 넣는다.
						if (!next.isVisited()) {
							next.visit();
							q.offer(next);
						}

					}

				}
			}
		}
	}

	/** isVisited[][]를 false로 초기화 */
	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}

	/** grid에서 CHEESE인 칸들의 개수 리턴 */
	public static int countCheese() {
		int ret = 0;

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (grid[y][x] == CHEESE) {
					ret++;
				}
			}
		}

		return ret;
	}
}