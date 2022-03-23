// 16968KB, 232ms

package bj11657;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static class Edge {
		int from;
		int to;
		long weight;

		public Edge(int from, int to, long weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}

	static class NegativeCycleException extends Exception {
		private static final long serialVersionUID = -3668927610436982197L;
	}

	static final long INF = 987654321987654321L;

	static int V, E;
	static Edge[] edges;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// edges 배열 메모리 할당
		edges = new Edge[E];

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			long weight = Long.parseLong(st.nextToken());

			edges[i] = new Edge(from, to, weight);
		}

		// 벨만 포드 알고리즘으로 1번 정점으로부터 다른 정점들까지의 최단 경로를 구한다.
		try {
			long[] dists = bellmanFord(1);
			for (int i = 2; i <= V; i++) {
				if (dists[i] == INF) {
					sb.append("-1").append("\n");
				} else {
					sb.append(dists[i]).append("\n");
				}
			}
			System.out.print(sb.toString());

		} catch (NegativeCycleException e) {
			System.out.println("-1"); // 음의 사이클이 존재하는 경우
		}

	} // end main

	/** 벨만-포드 알고리즘으로 start로부터 다른 정점들로의 최단 경로를 구하고, 음의 사이클이 존재한다면 예외를 발생시킨다 */
	public static long[] bellmanFord(int start) throws NegativeCycleException {
		long[] dists = new long[V + 1];
		Arrays.fill(dists, INF);
		dists[start] = 0L;

		// V번 수행
		for (int i = 0; i < V; i++) {
			// 모든 간선에 대해 dists 업데이트
			for (Edge e : edges) {
				int from = e.from;
				int to = e.to;
				long weight = e.weight;

				if (dists[from] != INF && dists[to] > dists[from] + weight) {
					dists[to] = dists[from] + weight;

					// V번째에도 업데이트가 된다면 음의 사이클 존재
					if (i == V - 1) {
						throw new NegativeCycleException();
					}
				}

			} // end for e

		} // end for i

		return dists;
	}
}