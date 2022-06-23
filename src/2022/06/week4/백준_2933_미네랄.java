// 68440KB, 256ms

package bj2933;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_R = 100, MAX_C = 100;
	static final char MINERAL = 'x', EMPTY = '.';
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final int INF = 987654321;

	static int R, C;

	static char[][] grid = new char[MAX_R + 1][MAX_C + 1];
	static Queue<Position> q = new ArrayDeque<>();
	static boolean[][] isVisited = new boolean[MAX_R + 1][MAX_C + 1];

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
			if (1 <= this.y && this.y <= R && 1 <= this.x && this.x <= C) {
				return true;
			}
			return false;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = R; y >= 1; y--) {
			String line = br.readLine();
			for (int x = 1; x <= C; x++) {
				grid[y][x] = line.charAt(x - 1);
			}
		}

		// 막대 던지는 횟수 입력
		int N = Integer.parseInt(br.readLine());

		// 시뮬레이션
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			// 막대 던질 높이 입력
			int height = Integer.parseInt(st.nextToken());

			// 왼쪽에서 오른쪽으로 던지는 경우
			if (i % 2 == 0) {
				throwStick(height, true);
			}
			// 오른쪽에서 왼쪽으로 던지는 경우
			else {
				throwStick(height, false);
			}

			// 클러스터 떨어뜨리기
			dropClusters();

		}

		// 정답 출력
		String answer = gridToString();
		System.out.println(answer);

	} // end main

	/** 클러스터 떨어뜨리기 시뮬레이션 */
	public static void dropClusters() {
		// 맨 아래 행과 연결된 미네랄들을 모두 탐색한다.
		bfsFromGround();

		// 땅과 연결되지 않은 미네랄들의 맨 아랫부분들을 구한다
		int[] clusterBottoms = new int[C + 1];
		for (int x = 1; x <= C; x++) {
			for (int y = 1; y <= R; y++) {
				if (!isVisited[y][x] && grid[y][x] == MINERAL) {
					clusterBottoms[x] = y;
					break;
				}
			}
		}

		// 그 아랫부분들로부터 땅과 연결된 부분까지의 거리를 구한다
		int minDist = INF;
		for (int x = 1; x <= C; x++) {
			if (clusterBottoms[x] == 0) {
				continue;
			}

			int dist = 0;
			for (int y = clusterBottoms[x] - 1; y >= 0; y--) {
				dist++;

				if (y == 0 || (isVisited[y][x] && grid[y][x] == MINERAL)) {
					break;
				}

			}

			minDist = Math.min(minDist, dist);
		}

		int move = minDist - 1;

		System.out.println(move);

		if (move != INF && move != 0) {
			for (int y = 1; y <= R; y++) {
				for (int x = 1; x <= C; x++) {
					if (!isVisited[y][x] && grid[y][x] == MINERAL) {
						grid[y - move][x] = grid[y][x];
						grid[y][x] = EMPTY;
					}
				}
			}
		}
	}

	/** 땅으로부터 연결된 미네랄들을 모두 방문한다 */
	public static void bfsFromGround() {
		// 큐, 방문 체크 배열 초기화
		q.clear();
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}

		// 시작 정점 처리
		for (int x = 1; x <= C; x++) {
			if (grid[1][x] == MINERAL) {
				Position pos = new Position(1, x);
				isVisited[pos.y][pos.x] = true;
				q.offer(pos);
			}
		}

		// bfs 수행
		while (!q.isEmpty()) {
			Position here = q.poll();

			for (int dir = 0; dir < DIRECTIONS; dir++) {
				Position next = here.getNextPos(dir);

				if (next.isInRange() && grid[next.y][next.x] == MINERAL && !isVisited[next.y][next.x]) {
					isVisited[next.y][next.x] = true;
					q.offer(next);
				}
			}
		}

		return;
	}

	/** height 높이에서 막대를 던진다. (fromLeft가 참이면 왼쪽에서 오른쪽으로, 거짓이면 오른쪽에서 왼쪽으로) */
	public static void throwStick(int height, boolean fromLeft) {
		// 탐색 방향 설정
		int startX = fromLeft ? 1 : C;
		int endX = fromLeft ? C : 1;
		int deltaX = fromLeft ? 1 : -1;

		for (int x = startX;; x += deltaX) {
			// 미네랄을 만나면 부수고 종료
			if (grid[height][x] == MINERAL) {
				grid[height][x] = EMPTY;
				break;
			}

			// 끝까지 도달한 경우 종료
			if (x == endX) {
				break;
			}
		}
	}

	/** grid의 상태를 문자열로 리턴한다 */
	public static String gridToString() {
		StringBuilder sb = new StringBuilder();

		for (int y = R; y >= 1; y--) {
			for (int x = 1; x <= C; x++) {
				sb.append(grid[y][x]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}