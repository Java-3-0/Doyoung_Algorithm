// 225484KB, 352ms

package bj7562;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int[] dy = { -2, -2, -1, -1, 1, 1, 2, 2 };
	static final int[] dx = { -1, 1, -2, 2, -2, 2, -1, 1 };
	static final int MAX_BOARDSIZE = 300;

	static int boardSize;
	static boolean[][] isVisited = new boolean[MAX_BOARDSIZE][MAX_BOARDSIZE];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public Position getNextPosition(int dir) {
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Position(nextY, nextX);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < boardSize && 0 <= this.x && this.x < boardSize) {
				return true;
			}

			return false;
		}

		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position p = (Position) obj;
				if (this.y == p.y && this.x == p.x) {
					return true;
				}
				return false;
			}

			return super.equals(obj);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 메모리 초기화
			initMemory();

			// 보드 크기 입력
			boardSize = Integer.parseInt(br.readLine());

			// 나이트 위치 입력
			st = new StringTokenizer(br.readLine(), " ");
			int xK = Integer.parseInt(st.nextToken());
			int yK = Integer.parseInt(st.nextToken());
			Position knight = new Position(yK, xK);

			// 목표 지점 위치 입력
			st = new StringTokenizer(br.readLine(), " ");
			int xT = Integer.parseInt(st.nextToken());
			int yT = Integer.parseInt(st.nextToken());
			Position target = new Position(yT, xT);

			// bfs로 목표 지점까지 가는 최단 이동 횟수를 구한다.
			int answer = bfs(knight, target);

			// 정답을 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

	/** bfs로 start에서 end까지의 최소 이동 횟수를 구해서 리턴한다 */
	public static int bfs(Position start, Position end) {
		Queue<Position> q = new LinkedList<>();
		start.visit();
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				if (here.equals(end)) {
					return depth;
				}

				for (int dir = 0; dir < dy.length; dir++) {
					Position there = here.getNextPosition(dir);
					if (there.isInRange() && !there.isVisited()) {
						there.visit();
						q.offer(there);
					}
				}

			} // end for i

			depth++;

		} // end while

		return depth;
	}

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}
