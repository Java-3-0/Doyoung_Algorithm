// 16144KB, 120ms

package bj1194;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언

	/** 그리드 최대 크기 */
	static final int MAX_H = 50, MAX_W = 50;
	/** 열쇠나 문의 종류 수 */
	static final int KEY_TYPES = 6;
	/** 맵 상에서 각 칸의 의미를 나타내는 상수 */
	static final char START = '0', END = '1', EMPTY = '.', WALL = '#';
	/** 사방탐색 방향 수 */
	static final int DIRECTIONS = 4;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { 0, 0, -1, 1 };

	// 전역변수 선언

	/** 그리드 크기 */
	static int H, W;
	/** 그리드 */
	static char[][] grid = new char[MAX_H][MAX_W];
	/** 각 상태의 방문 여부를 저장할 3차원 배열 */
	static boolean[][][] isVisited = new boolean[MAX_H][MAX_W][(1 << KEY_TYPES)];

	/** 상태 객체 */
	static class Status {
		int y;
		int x;
		int keys;

		/** y좌표, x좌표, 가지고 있는 열쇠 정보를 입력받아 상태 객체를 생성하는 생성자 */
		public Status(int y, int x, int keys) {
			super();
			this.y = y;
			this.x = x;
			this.keys = keys;
		}

		/** 현 상태를 방문한 적이 있었는지 여부를 리턴 */
		public boolean isVisited() {
			return isVisited[this.y][this.x][this.keys];
		}

		/** 현 상태를 방문하여 isVisited 배열 갱신 */
		public void visit() {
			isVisited[this.y][this.x][this.keys] = true;
		}

		/** 현 상태가 가리키는 위치가 grid 범위 내인지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}
			return false;
		}

		/** keys는 현재 상태 그대로 가지고서 dir 방향으로 한 칸 이동했을 때의 상태를 리턴 */
		public Status getNextStatus(int dir) {
			return new Status(y + dy[dir], x + dx[dir], keys);
		}

		/** 현 상태가 가리키는 위치의 grid 값을 리턴 */
		public char getGridVal() {
			return grid[this.y][this.x];
		}

		/** 디버깅을 위한 toString 함수 */
		@Override
		public String toString() {
			return "Status [y=" + y + ", x=" + x + ", keys=" + keys + "]";
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		// 현재 그리드 상태에서 미로를 탈출할 수 있는 최소 이동 횟수를 리턴
		int answer = getMinMoves();

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** 미로를 탈출할 수 있는 최소 이동 횟수를 계산해서 리턴 */
	public static int getMinMoves() {
		Queue<Status> q = new LinkedList<>();

		// 시작 위치를 찾아서 q에 넣는다.
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (grid[y][x] == START) {
					q.offer(new Status(y, x, 0));
				}
			}
		}

		// bfs 수행
		int depth = 1;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Status now = q.poll();

				for (int dir = 0; dir < DIRECTIONS; dir++) {
					// 사방탐색 다음 위치 계산
					Status next = now.getNextStatus(dir);

					// 그리드 범위 밖인 경우 무시
					if (!next.isInRange()) {
						continue;
					}

					// 다음 위치의 그리드 값
					char gridVal = next.getGridVal();

					// 출구를 만난 경우 종료
					if (gridVal == END) {
						return depth;
					}

					// 벽을 만난 경우 이동 실패
					else if (gridVal == WALL) {
						continue;
					}

					// 열쇠를 만난 경우 만난 열쇠 정보를 비트마스킹에 추가하고 큐에 넣기
					else if (isKey(gridVal)) {
						int keyNum = keyToInt(gridVal);
						int nextBitMask = now.keys | (1 << keyNum);
						next.keys = nextBitMask;

						if (!next.isVisited()) {
							next.visit();
							q.offer(next);
						}
					}

					// 문을 만난 경우, 해당 열쇠가 있을 때만 큐에 넣기
					else if (isDoor(gridVal)) {
						int doorNum = doorToInt(gridVal);

						// 열쇠가 있는 경우
						if ((now.keys & (1 << doorNum)) != 0) {
							if (!next.isVisited()) {
								next.visit();
								q.offer(next);
							}
						}
					}

					// 빈 칸 또는 시작 위치(빈 칸)을 만난 경우, 이동 가능하므로 큐에 넣기
					else {
						if (!next.isVisited()) {
							next.visit();
							q.offer(next);
						}
					}

				} // end for dir

			} // end for i

			depth++;

		} // end while

		// 탈출 실패
		return -1;
	}

	/** 문자 c를 문 번호로 변환한 수를 리턴 */
	public static int doorToInt(char c) {
		return c - 'A';
	}

	/** 문자 c를 열쇠 번호로 변환한 수를 리턴 */
	public static int keyToInt(char c) {
		return c - 'a';
	}

	/** 문자 c가 문을 나타내는지 여부를 리턴 */
	public static boolean isDoor(char c) {
		if ('A' <= c && c <= 'F') {
			return true;
		}
		return false;
	}

	/** 문자 c가 열쇠를 나타내는지 여부를 리턴 */
	public static boolean isKey(char c) {
		if ('a' <= c && c <= 'f') {
			return true;
		}

		return false;
	}

}