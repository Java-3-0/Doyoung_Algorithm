// 57536KB, 472ms

package bj1504;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 800;
	static final int MAX_E = 200000;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 행렬 초기화
		adjMatrix = new int[V + 1][V + 1];
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}

		// 인접 행렬 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			if (adjMatrix[from][to] > weight) {
				adjMatrix[from][to] = weight;
			}

			if (adjMatrix[to][from] > weight) {
				adjMatrix[to][from] = weight;
			}
			
		}

		// v1, v2 입력
		st = new StringTokenizer(br.readLine(), " ");
		int v1 = Integer.parseInt(st.nextToken());
		int v2 = Integer.parseInt(st.nextToken());

		// 1 -> v1 -> v2 -> N으로의 최단 경로 길이 계산
		long answer1 = (long) dijkstra(1, v1) + (long) dijkstra(v1, v2) + (long) dijkstra(v2, V);

		// 1 -> v2 -> v1 -> N으로의 최단 경로 길이 계산
		long answer2 = (long) dijkstra(1, v2) + (long) dijkstra(v2, v1) + (long) dijkstra(v1, V);

		// 두 경로 중 짧은 쪽이 정답
		long answer = answer1 < answer2 ? answer1 : answer2;

		// 정답 출력
		if (answer >= (long) INF) {
			System.out.println("-1");
		} else {
			System.out.println(answer);
		}

	} // end main

	/** 다익스트라 알고리즘을 이용하여 start에서 end로 가는 최소 경로를 리턴 */
	public static int dijkstra(int start, int end) {
		int[] dists = new int[V + 1];
		boolean[] isVisited = new boolean[V + 1];

		Arrays.fill(dists, INF);
		dists[start] = 0;

		for (int i = 0; i < V; i++) {
			int minDist = Integer.MAX_VALUE;
			int closestVertexIdx = 0;

			// 가장 가까운 정점 
			for (int v = 1; v <= V; v++) {
				int dist = dists[v];
				if (!isVisited[v] && dist < minDist) {
					minDist = dist;
					closestVertexIdx = v;
				}
			}

			// closeVertexIdx를 방문하고 거리 갱신
			isVisited[closestVertexIdx] = true;
			dists[closestVertexIdx] = minDist;

			// 아직 방문하지 않은 정점들에 대해서 closesetVertexIdx를 거쳐가는 경로를 사용해서 dists 갱신
			for (int to = 1; to <= V; to++) {
				if (!isVisited[to]) {
					if (adjMatrix[closestVertexIdx][to] != INF && dists[to] > dists[closestVertexIdx] + adjMatrix[closestVertexIdx][to]) {
						dists[to] = dists[closestVertexIdx] + adjMatrix[closestVertexIdx][to];
					}
				}
			}
		}

		return dists[end];
	}
}