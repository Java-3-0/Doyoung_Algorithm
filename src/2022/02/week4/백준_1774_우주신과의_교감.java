// 42632KB, 328ms

package bj1774;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	/** 위치를 나타내는 객체 */
	static class Position {
		/** x좌표 */
		double x;
		/** y좌표 */
		double y;

		/** x좌표와 y좌표를 받아서 위치 객체를 만드는 생성자 */
		public Position(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		/** 이 위치에서부터 위치 p까지의 거리를 리턴 */
		public double getDistanceTo(Position p) {
			double xDist = this.x - p.x;
			double yDist = this.y - p.y;

			double dist = Math.sqrt(xDist * xDist + yDist * yDist);
			return dist;
		}
	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		int V = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Position[] stars = new Position[V];
		for (int i = 0; i < V; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			stars[i] = new Position(x, y);
		}
		
		// Prim 알고리즘을 위한 배열들을 선언
		double[][] adjMatrix = new double[V][V];
		PriorityQueue<Double>[] minEdgePQ = new PriorityQueue[V];
		for (int i = 0; i < V; i++) {
			minEdgePQ[i] = new PriorityQueue<Double>();
		}

		boolean[] isVisited = new boolean[V];

		// 모든 정점 쌍에 대해서 거리를 계산해서 adjMatrix에 저장하고, 각 정점으로의 minDist도 최대값으로 초기화
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				adjMatrix[y][x] = stars[y].getDistanceTo(stars[x]);
			}
			minEdgePQ[y].offer(Double.MAX_VALUE);
		}
		
		// 이미 연결된 정점들의 경우, 둘 사이의 간선 weight을 0으로 만든다.
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int idx1 = Integer.parseInt(st.nextToken()) - 1;
			int idx2 = Integer.parseInt(st.nextToken()) - 1;
			
			adjMatrix[idx1][idx2] = 0.0;
			adjMatrix[idx2][idx1] = 0.0;
		}

		// 0번 정점에서 시작
		minEdgePQ[0].offer(0.0);
		double answer = 0.0;

		// Prim 알고리즘으로 최소 스패닝 트리를 만든다.
		for (int countVisited = 0; countVisited < V; countVisited++) {
			double min = Double.MAX_VALUE;
			int minVertexIdx = 0;

			// 최소 간선을 선택한다.
			for (int v = 0; v < V; v++) {
				if (!isVisited[v] && minEdgePQ[v].peek() < min) {
					min = minEdgePQ[v].peek();
					minVertexIdx = v;
				}
			}

			// 이 간선을 택해서 방문 여부를 갱신하고, 정답에 간선 길이를 더한다.
			isVisited[minVertexIdx] = true;
			answer += min;

			// minEdgePQ 우선순위 큐 갱신
			for (int v = 0; v < V; v++) {
				if (!isVisited[v]) {
					minEdgePQ[v].offer(adjMatrix[minVertexIdx][v]);
				}
			}
		}

		// 소수점 2자리까지 출력
		System.out.printf("%.2f\n", answer);

	} // end main
}