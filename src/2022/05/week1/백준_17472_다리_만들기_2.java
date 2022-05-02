// 11768KB, 80ms

package bj17472;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 10, MAX_W = 10;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final int INF = 987654;
	static final int FAIL = -1;

	static int H, W;
	static int[][] input = new int[MAX_H][MAX_W];
	static int[][] grid = new int[MAX_H][MAX_W];
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];
	static int groupCnt = 0;
	static int[][] adjMatrix;

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

	}

	static class Edge implements Comparable<Edge> {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 입력
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				input[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 입력 내용대로 그룹 번호를 매긴다
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (!isVisited[y][x] && input[y][x] == 1) {
					groupCnt++;
					findGroup(y, x);
				}
			}
		}

		// 그룹 간의 간선 정보를 만든다
		makeAdjMatrix();

		// mst 길이 계산
		int mst = prim();

		// 출력
		System.out.println(mst);

	} // end main

	/** 섬들 사이의 다리들을 놓아 adjMatrix를 만든다 */
	public static void makeAdjMatrix() {
		// 메모리 할당 후 초기화
		adjMatrix = new int[groupCnt + 1][groupCnt + 1];
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}

		// 가로 방향 다리 시도
		for (int y = 0; y < H; y++) {
			int prev = grid[y][0];
			int bridgeLength = 0;
			for (int x = 1; x < W; x++) {
				int now = grid[y][x];

				if (prev == 0) {
					bridgeLength = 0;
					prev = now;
					continue;
				}

				if (now == prev) {
					bridgeLength = 0;
					continue;
				}

				if (now == 0) {
					bridgeLength++;
				}

				else {
					if (bridgeLength >= 2) {
						adjMatrix[prev][now] = Math.min(adjMatrix[prev][now], bridgeLength);
						adjMatrix[now][prev] = Math.min(adjMatrix[now][prev], bridgeLength);
					}

					prev = now;
					bridgeLength = 0;
				}
			}
		}

		// 세로 방향 다리 시도
		for (int x = 0; x < W; x++) {
			int prev = grid[0][x];
			int bridgeLength = 0;
			for (int y = 1; y < H; y++) {
				int now = grid[y][x];

				if (prev == 0) {
					prev = now;
					bridgeLength = 0;
					continue;
				}

				if (now == prev) {
					bridgeLength = 0;
					continue;
				}

				if (now == 0) {
					bridgeLength++;
				}

				else {
					if (bridgeLength >= 2) {
						adjMatrix[prev][now] = Math.min(adjMatrix[prev][now], bridgeLength);
						adjMatrix[now][prev] = Math.min(adjMatrix[now][prev], bridgeLength);
					}

					prev = now;
					bridgeLength = 0;
				}
			}
		}
	} // end makeAdjMatrix

	/** (startY, startX)로부터 이어진 땅을 groupNum번 그룹으로 표시한다 */
	public static void findGroup(int startY, int startX) {
		Queue<Position> q = new LinkedList<>();

		isVisited[startY][startX] = true;
		q.offer(new Position(startY, startX));

		while (!q.isEmpty()) {
			Position now = q.poll();
			grid[now.y][now.x] = groupCnt;

			for (int dir = 0; dir < dy.length; dir++) {
				Position next = now.getNextPos(dir);
				if (next.isInRange() && !isVisited[next.y][next.x] && input[next.y][next.x] == 1) {
					isVisited[next.y][next.x] = true;
					q.offer(next);
				}
			}
		}
	} // end findGroup

	/** prim알고리즘으로 mst길이를 구해서 리턴 */
	public static int prim() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] isSelected = new boolean[groupCnt + 1];

		// 시작 정점 처리
		int start = 1;
		pq.offer(new Edge(start, 0));

		// Prim 알고리즘 수행
		int ret = 0;
		int cnt = 0;
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge e = pq.poll();
			int now = e.to;
			if (isSelected[now]) {
				continue;
			}

			isSelected[now] = true;
			ret += e.weight;
			cnt++;

			// 연결된 간선들을 pq에 넣는다
			for (int next = 1; next <= groupCnt; next++) {
				if (!isSelected[next] && adjMatrix[now][next] != INF) {
					pq.offer(new Edge(next, adjMatrix[now][next]));
				}
			}
		}

		if (cnt == groupCnt) {
			return ret;
		} else {
			return FAIL;
		}
	} // end prim
}