package bj17141;

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
	static final int MAX_N = 50;
	/** 바이러스 최대 개수 */
	static final int MAX_M = 10;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { 0, 0, -1, 1 };
	/** 무한 */
	static final int INF = 987654321;

	// 전역변수 선언

	/** 그리드 크기 */
	static int N;
	/** 선택할 바이러스의 개수 */
	static int M;
	/** 전체 바이러스의 개수 */
	static int totalViruses = 0;
	/** 전체 벽의 개수 */
	static int totalWalls = 0;
	/** 방문해야 하는 칸 수 */
	static int targetCnt;
	/** 그리드 */
	static int[][] grid;
	/** 바이러스의 위치들 */
	static List<Position> virusPositions;
	/** 조합 */
	static int[] combi;
	/** 방문 여부 */
	static boolean[][] isVisited;
	/** 모든 빈 칸을 방문하는 최소 시간 */
	static int minTime = INF;

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
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
				return true;
			}
			return false;
		}

		/** 위치에서 dir 방향으로 한 칸 이동한 다음 위치를 리턴 */
		public Position getNextPosition(int dir) {
			int nextY = this.y + dy[dir];
			int nextX = this.x + dx[dir];

			return new Position(nextY, nextX);
		}

		/** 그리드에서 이 위치의 값을 가져와서 리턴한다 */
		public int getVal() {
			return grid[this.y][this.x];
		}

		/** 위치 방문해서 isVisited 배열 갱신 */
		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		/** 위치가 이미 방문되었는지 여부를 리턴 */
		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		grid = new int[N][N];
		combi = new int[M];
		isVisited = new boolean[N][N];
		virusPositions = new ArrayList<>();

		// 그리드 입력
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				int input = Integer.parseInt(st.nextToken());
				grid[y][x] = input;
				if (input == 2) {
					totalViruses++;
					virusPositions.add(new Position(y, x));
				} else if (input == 1) {
					totalWalls++;
				}
			}
		}

		// 방문해야 하는 칸 수 계산
		targetCnt = N * N - totalWalls;

		// 정답 계산해서 출력
		if (targetCnt == 0) {
			System.out.println(0);
		} else {
			// 바이러스를 선택하는 모든 조합 완전탐색
			combination(0, 0);
			int answer = minTime == INF ? -1 : minTime;
			System.out.println(answer);
		}

	} // end main

	/** C(totalViruses, M)개의 조합 완전탐색하며 minTime 갱신 */
	public static void combination(int hereIdx, int cnt) {
		// M개를 선택하여 조합이 완성된 경우, 이 조합의 바이러스들로 bfs 수행
		if (cnt == M) {
			int time = getTimeToVisitAll();
			minTime = time < minTime ? time : minTime;
			return;
		}

		// M개를 선택하지 못하고 끝까지 간 경우 리턴
		if (hereIdx == totalViruses) {
			return;
		}

		// 조합을 만들어 본다.
		combi[cnt] = hereIdx;
		combination(hereIdx + 1, cnt + 1);
		combination(hereIdx + 1, cnt);
	}

	/**
	 * 현재 combi[]를 활성화된 바이러스들로 하여, bfs를 수행
	 * 
	 * @return 벽이 아닌 모든 칸 방문 성공 시, 방문하기까지의 시간. 실패 시, INF.
	 */
	public static int getTimeToVisitAll() {
		// 새로 bfs를 돌릴 것이므로 isVisited 초기화
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}

		// bfs 초기 세팅
		Queue<Position> q = new LinkedList<>();
		for (int idx : combi) {
			Position p = virusPositions.get(idx);
			p.visit();
			q.offer(p);
		}

		// bfs 수행
		int depth = 0;
		int cnt = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			// 한 depth씩 수행
			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				cnt++;
				if (cnt == targetCnt) {
					return depth;
				}

				// 사방 탐색으로 다음 위치가 그리드 범위 내이고, 방문하지 않았고, 벽이 아니라면 큐에 넣는다.
				for (int dir = 0; dir < dy.length; dir++) {
					Position there = here.getNextPosition(dir);
					if (there.isInRange() && !there.isVisited() && there.getVal() != 1) {
						there.visit();
						q.offer(there);
					}
				}
			}

			depth++;
		}

		// 큐가 빌 때까지 모든 빈 칸 방문에 실패했다면 INF를 리턴
		return INF;
	}
}