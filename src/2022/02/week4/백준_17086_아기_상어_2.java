// 12608KB, 96ms

package bj17086;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	/** 공간의 최대 높이, 최대 너비 */
	static final int MAX_HEIGHT = 50, MAX_WIDTH = 50;
	/** 8방탐색 방향의 개수 */
	static final int DIRECTIONS = 8;
	/** 8방탐색 y 변화량 */
	static final int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };
	/** 8방탐색 x 변화량 */
	static final int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };

	/** 공간의 높이, 너비 */
	static int height, width;
	/** 입력받은 공간 상태를 나타내는 배열 */
	static int[][] grid = new int[MAX_HEIGHT][MAX_WIDTH];
	/** 방문 여부를 나타내는 배열 */
	static boolean[][] isVisited = new boolean[MAX_HEIGHT][MAX_WIDTH];

	/** 위치 객체 */
	static class Position {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;

		/** y좌표와 x좌표를 파라미터로 받아서 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 위치가 그리드 범위 내에 들어가는지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < height && 0 <= this.x && this.x < width) {
				return true;
			}
			return false;
		}

		/** dir 방향으로의 다음 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}
	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 모든 칸의 안전 거리를 계산
		int[][] safeDists = getSafeDistances();

		// 안전 거리 중 최대값을 찾음
		int answer = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int dist = safeDists[y][x];
				answer = answer < dist ? dist : answer;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	public static int[][] getSafeDistances() {
		int[][] ret = new int[height][width];

		Queue<Position> q = new LinkedList<>();

		// 상어의 위치들 모두가 bfs의 시작점이 되므로, 모든 상어의 위치를 q에 넣는다.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (grid[y][x] == 1) {
					isVisited[y][x] = true;
					q.offer(new Position(y, x));
				}
			} // end for x
		} // end for y

		// bfs를 통해 각 칸까지의 안전 거리를 구해서 ret에 넣는다.
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				ret[here.y][here.x] = depth;

				for (int dir = 0; dir < DIRECTIONS; dir++) {
					Position there = here.getNextPos(dir);
					if (there.isInRange() && !isVisited[there.y][there.x]) {
						isVisited[there.y][there.x] = true;
						q.offer(there);
					}
				}

			}

			depth++;
		} // end while

		return ret;
	}
}