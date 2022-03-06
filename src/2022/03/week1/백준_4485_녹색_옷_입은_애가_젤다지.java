// 22492KB, 220ms

package bj4485;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 125;
	static final int INF = 987654321;
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];

	/** 위치 객체 */
	static class Position {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;

		/** y좌표와 x좌표를 받아서 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 위치가 그리드 범위 내라면 true, 아니면 false를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
				return true;
			}
			return false;
		}

		/** 위치에서 dir 방향으로 1칸 이동한 다음 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}
	}

	/** 다익스트라 알고리즘 우선순위 큐에 담기 위한 정점 객체 */
	static class Vertex implements Comparable<Vertex> {
		/** 정점 번호 */
		Position pos;
		/** 시작 정점부터 이 정점까지 오는 거리 */
		int dist;

		/** 정점 번호, 시작 정점으로부터의 거리를 받아서 정점 객체를 생성하는 생성자 */
		public Vertex(Position pos, int dist) {
			super();
			this.pos = pos;
			this.dist = dist;
		}

		/** 거리 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Vertex v) {
			return this.dist - v.dist;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int testCase = 0;
		while (true) {
			testCase++;
			
			// N 입력
			N = Integer.parseInt(br.readLine());

			// 0이 입력되면 종료
			if (N == 0) {
				break;
			}

			// 동굴 상태 입력
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < N; x++) {
					grid[y][x] = Integer.parseInt(st.nextToken());
				}
			}

			// 잃을 수밖에 없는 최소 루피 계산
			int[][] losses = getminLosses();
			int answer = losses[N - 1][N - 1];
			
			// 출력 스트링빌더에 정답 추가
			sb.append("Problem ").append(testCase).append(": ").append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 다익스트라 알고리즘을 이용하여, 각 위치까지 가는 데 잃을 수밖에 없는 최소 금액을 2차원 배열 형태로 리턴 */
	public static int[][] getminLosses() {
		// 다익스트라 알고리즘 관련 메모리 할당 및 초기화
		int[][] dists = new int[N][N];
		boolean[][] isVisited = new boolean[N][N];
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		for (int i = 0; i < N;i ++) {
			Arrays.fill(dists[i], INF);
		}
		
		// 첫 칸 처리
		dists[0][0] = grid[0][0];
		pq.offer(new Vertex(new Position(0, 0), dists[0][0]));

		// pq가 빌 때까지 반복
		while (!pq.isEmpty()) {
			// 아직 방문하지 않은 정점이 나올 때까지 뽑아온다.
			Vertex v = pq.poll();
			Position here = v.pos;
			int distToHere = v.dist;
			if (isVisited[here.y][here.x]) {
				continue;
			}
			
			// 정점을 방문하고, 그곳까지의 최단 거리를 업데이트
			isVisited[here.y][here.x] = true;
			dists[here.y][here.x] = distToHere;
			
			// 그 정점과 인접한 다른 정점들에 대해 거리가 업데이트된다면 pq에 넣는다.
			for (int dir = 0; dir < DIRECTIONS; dir++) {
				Position there = here.getNextPos(dir);
				if (there.isInRange() && !isVisited[there.y][there.x]) {
					int distToThere = dists[here.y][here.x] + grid[there.y][there.x];
					if (distToThere < dists[there.y][there.x]) {
						pq.offer(new Vertex(there, distToThere));
					}
				}
			}
		}
		
		return dists;
	}
}