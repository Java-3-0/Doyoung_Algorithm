// 22736KB, 151ms

package swea1249;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Solution {
	static final int MAX_N = 100;
	static final int[] dy = { 0, 1, 0, -1 };
	static final int[] dx = { 1, 0, -1, 0 };

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];

	/** 다익스트라 알고리즘에서 우선순위 큐에 들어갈 정점 객체 */
	static class Vertex implements Comparable<Vertex> {
		int y;
		int x;
		int dist;

		public Vertex(int y, int x, int dist) {
			super();
			this.y = y;
			this.x = x;
			this.dist = dist;
		}

		/** dist 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Vertex v) {
			return this.dist - v.dist;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {

			// 그리드 크기 입력
			N = Integer.parseInt(br.readLine());

			// 그리드 입력
			for (int y = 0; y < N; y++) {
				String line = br.readLine();
				for (int x = 0; x < N; x++) {
					grid[y][x] = line.charAt(x) - '0';
				}
			}

			// 출발지부터 도착지까지 가는 최소 시간을 계산
			int answer = getMinTime(0, 0);

			// 정답을 형식에 맞게 출력 스트링빌더에 추가
			sb.append("#").append(tc).append(" ").append(answer).append("\n");

		}

		// 출력
		System.out.print(sb.toString());

	}

	/** 다익스트라 알고리즘을 이용하여 (startY, startX) 위치부터 우측 하단까지 이동하는 최소 시간을 계산해서 리턴 */
	public static int getMinTime(int startY, int startX) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		boolean[][] isVisited = new boolean[N][N];

		// 시작 지점 처리
		isVisited[startY][startX] = true;
		pq.offer(new Vertex(startY, startX, 0));

		// 다익스트라 알고리즘 수행
		while (!pq.isEmpty()) {
			Vertex here = pq.poll();
			if (here.y == N - 1 && here.x == N - 1) {
				return here.dist;
			}

			for (int dir = 0; dir < dy.length; dir++) {
				int nextY = here.y + dy[dir];
				int nextX = here.x + dx[dir];

				if (isInRange(nextY, nextX) && !isVisited[nextY][nextX]) {
					isVisited[nextY][nextX] = true;
					pq.offer(new Vertex(nextY, nextX, here.dist + grid[nextY][nextX]));
				}
			}
		}

		return -1;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < N && 0 <= x && x < N) {
			return true;
		}
		return false;
	}

}
