// 25232KB, 284ms

package bj11657;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static class Edge {
		int to;
		long weight;

		public Edge(int to, long weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}

	static class NegativeCycleException extends Exception {
		private static final long serialVersionUID = -3668927610436982197L;
	}

	static final int MAX_V = 500;
	static final int MAX_E = 6000;
	static final long INF = 987654321987654321L;
	static final long BIG_NUM = 100000000000L;

	static int V, E;
	static List<Edge>[] adjList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 리스트 공간 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 인접 리스트 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			long weight = Long.parseLong(st.nextToken());

			adjList[from].add(new Edge(to, weight));
		}

		// 벨만 포드 알고리즘으로 1번 정점으로부터 각 정점까지의 최단 경로를 구한다.
		try {
			long[] dist = bellmanFord(1);
			for (int i = 2; i <= V; i++) {
				if (dist[i] > INF - BIG_NUM) {
					sb.append("-1").append("\n");
				} else {
					sb.append(dist[i]).append("\n");
				}
			}
			System.out.print(sb.toString());

		} catch (NegativeCycleException e) {
			System.out.println("-1"); // 음의 사이클이 존재하는 경우
		}

	} // end main

	/** 벨만-포드 알고리즘으로 start로부터 다른 정점들로의 최단 경로를 구하고, 음의 사이클이 존재한다면 예외를 발생시킨다 */
	public static long[] bellmanFord(int start) throws NegativeCycleException {
		long[] dist = new long[V + 1];
		Arrays.fill(dist, INF);
		dist[start] = 0;

		// V번 수행
		for (int i = 0; i < V; i++) {
			// 모든 간선에 대해 dist 업데이트
			for (int from = 1; from <= V; from++) {
				for (Edge e : adjList[from]) {
					int to = e.to;

					if (dist[from] != INF && dist[to] > dist[from] + e.weight) {
						dist[to] = dist[from] + e.weight;
						
						// V번째에도 업데이트가 된다면 음의 사이클 존재
						if (i == V - 1) {
							throw new NegativeCycleException();
						}
					}
				}
			}
		}

		return dist;
	}
}