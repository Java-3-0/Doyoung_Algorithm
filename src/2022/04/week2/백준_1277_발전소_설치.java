// 34344KB, 304ms

package bj1277;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000, MAX_W = 10000;
	static final double MAX_M = 200000.0;
	static final double INF = 9876543219876543.0;

	static int N, W;
	static double M;
	static Position[] positions;
	static double[][] adjMatrix;

	/** 위치 객체 */
	static class Position {
		double x;
		double y;

		public Position(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getDistanceTo(Position p) {
			double distX = Math.abs(this.x - p.x);
			double distY = Math.abs(this.y - p.y);
			double dist = Math.sqrt(distX * distX + distY * distY);
			return dist;
		}
	}

	/** 다익스트라 알고리즘 우선순위 큐에 넣기 위한 정점 객체 */
	static class Vertex implements Comparable<Vertex> {
		int vertexNum;
		double dist;

		public Vertex(int vertexNum, double dist) {
			super();
			this.vertexNum = vertexNum;
			this.dist = dist;
		}

		@Override
		public int compareTo(Vertex v) {
			if (this.dist < v.dist) {
				return -1;
			} else if (this.dist == v.dist) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public String toString() {
			return "Vertex [vertexNum=" + vertexNum + ", dist=" + dist + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// M 입력
		M = Double.parseDouble(br.readLine());

		// 메모리 할당
		positions = new Position[N + 1];
		adjMatrix = new double[N + 1][N + 1];

		// 위치 입력
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 거리를 구해서 adjMatrix 계산
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				double dist = positions[i].getDistanceTo(positions[j]);
				adjMatrix[i][j] = dist;
			}
		}

		// 이미 연결된 지점들의 adjMatrix 값을 0으로 갱신
		for (int i = 0; i < W; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjMatrix[from][to] = 0.0;
			adjMatrix[to][from] = 0.0;
		}

		// 다익스트라 알고리즘으로 최단 경로 계산
		double minLength = dijkstra(1, N);

		// 정답 출력
		if (minLength == INF) {
			System.out.println(-1);
		} else {
			long answer = (long) Math.floor(minLength * 1000.0);
			System.out.println(answer);
		}

	} // end main

	/** 다익스트라 알고리즘을 수행하여 start부터 end로의 최소 경로 길이 계산해서 리턴 */
	public static double dijkstra(int start, int end) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		boolean[] isVisited = new boolean[N + 1];

		pq.offer(new Vertex(start, 0.0));

		while (!pq.isEmpty()) {
			Vertex here = pq.poll();
			if (isVisited[here.vertexNum]) {
				continue;
			}
			
			isVisited[here.vertexNum] = true;
			if (here.vertexNum == end) {
				return here.dist;
			}

			for (int i = 1; i <= N; i++) {
				if (!isVisited[i] && adjMatrix[here.vertexNum][i] <= M) {
					pq.offer(new Vertex(i, here.dist + adjMatrix[here.vertexNum][i]));
				}
			}
		}

		return INF;
	}

}