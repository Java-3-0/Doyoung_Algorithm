// 19972KB, 136ms

package bj17244;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 50, MAX_W = 50;
	static final char EMPTY = '.', WALL = '#', START = 'S', STUFF = 'X', END = 'E';
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
	static boolean[][][] isVisited = new boolean[MAX_H][MAX_W][(1 << 5)];
	static int stuffCnt = 0;

	static class Status {
		int y;
		int x;
		int having;

		public Status(int y, int x, int having) {
			super();
			this.y = y;
			this.x = x;
			this.having = having;
		}

		/** 현 상태에서의 위치가 그리드 범위 내인지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}
			return false;
		}

		/** 물건들을 그대로 가지고 dir 방향으로 한 칸 이동한 상태를 리턴 */
		public Status getNextStatus(int dir) {
			return new Status(y + dy[dir], x + dx[dir], this.having);
		}

		public boolean isVisited() {
			return isVisited[this.y][this.x][this.having];
		}

		public void visit() {
			isVisited[this.y][this.x][this.having] = true;
		}

		public char getGridVal() {
			return grid[this.y][this.x];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		int startY = 0, startX = 0;
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				char input = line.charAt(x);
				if (input == STUFF) {
					grid[y][x] = (char) (stuffCnt++);
				} else {
					grid[y][x] = input;

					if (input == START) {
						startY = y;
						startX = x;
					}
				}
			}
		}

		int answer = getMinTime(startY, startX);

		System.out.println(answer);

	} // end main

	public static int getMinTime(int startY, int startX) {
		Queue<Status> q = new LinkedList<>();

		Status start = new Status(startY, startX, 0);
		start.visit();
		q.offer(start);

		int depth = 1;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Status now = q.poll();

				for (int dir = 0; dir < DIRECTIONS; dir++) {
					Status next = now.getNextStatus(dir);

					if (next.isInRange()) {
						char gridVal = next.getGridVal();
						if (gridVal == END) {
							if (next.having == (1 << stuffCnt) - 1) {
								return depth;
							}
						} else if (gridVal == WALL) {
							continue;
						} else if (0 <= gridVal && gridVal < 5) {
							next.having |= (1 << gridVal);
						}

						if (!next.isVisited()) {
							next.visit();
							q.offer(next);
						}

					}
				} // end for dir

			} // end for i

			depth++;

		} // end while

		return -1;
	}

}