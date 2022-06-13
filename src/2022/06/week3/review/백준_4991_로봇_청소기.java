// 92300KB, 332ms

package bj4991;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 20;
	static final int MAX_W = 20;
	static final int MAX_DUSTS = 10;
	static final int MAX_BIT = (1 << MAX_DUSTS) - 1;
	static final char CLEAN = '.', DUST = '*', WALL = 'x', ROBOT = 'o';

	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final int FAIL = -1;

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
	static boolean[][][] visitCheck = new boolean[MAX_H][MAX_W][MAX_BIT + 1];
	static Queue<Status> q = new ArrayDeque<>();
	static int dirtyCnt = 0;
	static int targetBitMask = 0;

	static class Status {
		int y;
		int x;
		int bitMask;

		public Status(int y, int x, int bitMask) {
			super();
			this.y = y;
			this.x = x;
			this.bitMask = bitMask;
		}

		public Status getNextStatus(int dir, int nextBitMask) {
			// 다음 위치 계산
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Status(nextY, nextX, nextBitMask);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}
			return false;
		}

		public char getGridVal() {
			return grid[this.y][this.x];
		}

		public boolean canGo() {
			if (this.isInRange() && this.getGridVal() != WALL) {
				return true;
			}
			return false;
		}

		public boolean isVisited() {
			return visitCheck[this.y][this.x][this.bitMask];
		}

		public void visit() {
			visitCheck[this.y][this.x][this.bitMask] = true;
		}

		@Override
		public String toString() {
			return "Status [y=" + y + ", x=" + x + ", bitMask=" + bitMask + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while (true) {
			// 메모리 초기화
			initMemory();

			// 그리드 크기 입력
			st = new StringTokenizer(br.readLine(), " ");
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			// 종료 조건
			if (W == 0 && H == 0) {
				break;
			}

			// 그리드 입력받기 (먼지는 숫자 '0'에서 '9'까지 값으로 순서대로 그리드에 등록)
			int startY = 0;
			int startX = 0;
			for (int y = 0; y < H; y++) {
				String line = br.readLine();
				for (int x = 0; x < W; x++) {
					char c = line.charAt(x);
					grid[y][x] = c;

					// 로봇 시작 위치와 먼지 개수도 함께 계산
					if (c == ROBOT) {
						startY = y;
						startX = x;
					} else if (c == DUST) {
						grid[y][x] = (char) ('0' + dirtyCnt);
						dirtyCnt++;
					}
				}
			}

			// 목표하는 비트마스크 계산
			targetBitMask = (1 << dirtyCnt) - 1;

			// bfs 시작 위치 설정
			Status start = new Status(startY, startX, 0);

			// bfs 수행하며 정답 계산
			int answer = bfs(start);

			// 정답을 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** bfs를 수행하며 모든 먼지를 청소할 때까지의 최소 이동 횟수를 리턴, 전부 청소에 실패 시 FAIL을 리턴 */
	public static int bfs(Status start) {
		// 시작 정점 처리
		start.visit();
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();

			for (int i = 0; i < size; i++) {
				Status now = q.poll();
				
				int nextBitMask = now.bitMask;

				// 먼지일 경우 먼지 번호 계산해서 비트마스킹 갱신
				char gridVal = now.getGridVal();
				if (isDirty(gridVal)) {
					int dustIdx = getDustIdx(gridVal);
					nextBitMask |= (1 << dustIdx);
				}

				// 도착한 경우 종료
				if (nextBitMask == targetBitMask) {
					return depth;
				}

				// 다음 정점 큐에 추가
				for (int dir = 0; dir < DIRECTIONS; dir++) {
					Status next = now.getNextStatus(dir, nextBitMask);
					if (next.canGo() && !next.isVisited()) {
						next.visit();
						q.offer(next);
					}
				}
			}

			depth++;
		}

		return FAIL;
	}

	// 전역변수 메모리 초기화
	public static void initMemory() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], CLEAN);
		}

		for (int i = 0; i < visitCheck.length; i++) {
			for (int j = 0; j < visitCheck[i].length; j++) {
				Arrays.fill(visitCheck[i][j], false);
			}
		}

		q.clear();

		dirtyCnt = 0;
		targetBitMask = 0;
	}

	/** 문자 c가 먼지를 나타내는지 여부를 리턴 */
	public static boolean isDirty(char c) {
		if ('0' <= c && c <= '9') {
			return true;
		}
		return false;
	}

	/** 문자 c의 먼지 번호를 리턴 */
	public static int getDustIdx(char c) {
		return c - '0';
	}

}