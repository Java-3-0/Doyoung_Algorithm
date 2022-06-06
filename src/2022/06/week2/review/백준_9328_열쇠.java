// 55388KB, 324ms

package bj9328;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100, MAX_W = 100;
	static final char EMPTY = '.', WALL = '*', TARGET = '$';
	static final int ALPHABETS = 26;
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final Position startPos = new Position(0, 0);

	/** 그리드 높이, 너비 */
	static int H, W;
	/** 그리드 */
	static char[][] grid = new char[MAX_H + 2][MAX_W + 2];
	/** 방문 체크 배열 */
	static boolean[][] isVisited = new boolean[MAX_H + 2][MAX_W + 2];
	/** bfs를 위한 큐 */
	static Queue<Position> q = new ArrayDeque<>();
	/** 가지고 있는 열쇠 정보를 나타내는 비트마스크 */
	static int myKeys = 0;
	/** 문서 획득 개수를 나타내는 변수 */
	static int answer = 0;

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
			return "Pos [y=" + y + ", x=" + x + "]";
		}

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y <= H + 1 && 0 <= this.x && this.x <= W + 1) {
				return true;
			}
			return false;
		}

		public boolean isVisited() {
			if (isVisited[this.y][this.x]) {
				return true;
			}
			return false;
		}

		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		public char getGridVal() {
			return grid[this.y][this.x];
		}

		public void emptyGrid() {
			grid[this.y][this.x] = EMPTY;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// 그리드 높이, 너비 입력
			st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			// 그리드 입력
			for (int y = 1; y <= H; y++) {
				String line = br.readLine();
				for (int x = 1; x <= W; x++) {
					grid[y][x] = line.charAt(x - 1);
				}
			}

			// 처음부터 가지고 있는 키들 입력
			String initialKeys = br.readLine();
			if (!initialKeys.equals("0")) {
				for (int i = 0; i < initialKeys.length(); i++) {
					int keyNum = getKeyNum(initialKeys.charAt(i));
					myKeys |= (1 << keyNum);
				}
			}

			// 새로운 열쇠를 더 이상 얻지 않을 때까지 bfs 수행
			while (true) {
				if (!bfs(startPos)) {
					break;
				}
			}

			// 정답을 출력용 스트링빌더에 추가
			sb.append(answer).append("\n");

		} // end for tc

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], EMPTY);
		}
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}

		q.clear();
		myKeys = 0;
		answer = 0;
	}

	/** 문자가 열쇠를 나타내는지 여부를 리턴 */
	public static boolean isKey(char c) {
		if ('a' <= c && c <= 'z') {
			return true;
		}
		return false;
	}

	/** 문자가 문을 나타내는지 여부를 리턴 */
	public static boolean isDoor(char c) {
		if ('A' <= c && c <= 'Z') {
			return true;
		}
		return false;
	}

	/** 문자가 나타내는 열쇠 번호를 리턴 */
	public static int getKeyNum(char c) {
		return c - 'a';
	}

	/** 문자가 나타내는 문 번호를 리턴 */
	public static int getDoorNum(char c) {
		return c - 'A';
	}

	/** 문을 열 수 있는 키를 가지고 있는지 여부를 리턴 */
	public static boolean haveKey(int doorNum) {
		if ((myKeys & (1 << doorNum)) != 0) {
			return true;
		}
		return false;
	}

	/** bfs를 수행하고, 더 이상 새로운 열쇠를 얻지 못한다면 false를 리턴 */
	public static boolean bfs(Position start) {
		// 가지고 있었던 키 정보를 복사
		int nextKeys = myKeys;

		// 초기화
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
		q.clear();

		// 시작 정점 처리
		isVisited[start.y][start.x] = true;
		q.offer(start);

		// bfs 수행
		while (!q.isEmpty()) {
			// 큐에서 poll한다
			Position now = q.poll();
			char nowGridVal = now.getGridVal();

			// 열쇠이면 획득한다
			if (isKey(nowGridVal)) {
				int keyNum = getKeyNum(nowGridVal);
				nextKeys |= (1 << keyNum);
				now.emptyGrid();
			}

			// 문서이면 획득한다
			else if (nowGridVal == TARGET) {
				answer++;
				now.emptyGrid();
			}

			// 사방탐색으로 다음 정점을 탐색한다
			for (int dir = 0; dir < DIRECTIONS; dir++) {
				Position next = now.getNextPos(dir);

				// 범위 내의 새로 방문하는 칸인 경우
				if (next.isInRange() && !next.isVisited()) {
					char nextGridVal = next.getGridVal();

					// 벽이나, 열쇠를 가지지 않은 문은 갈 수 없다
					if ((nextGridVal == WALL) || (isDoor(nextGridVal) && !haveKey(getDoorNum(nextGridVal)))) {
						continue;
					}

					// 이외의 경우 갈 수 있다
					next.visit();
					q.offer(next);
				}
			}
		}

		// 결과 리턴
		if (nextKeys == myKeys) {
			return false;
		}

		else {
			myKeys = nextKeys;
			return true;
		}
	}

}