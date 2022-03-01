// 67228KB, 3400ms

package bj1865;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500;
	static final int MAX_E = 2500 + 200;
	static final long INF 		= 98765432198765432L;
	static final long BIG_NUM 	= 100000000000000L;

	static int V, E;
	static List<Edge> edges = new ArrayList<>();

	static class NegCycleException extends Exception {
		private static final long serialVersionUID = -8788190071883944764L;
	}

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

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			E = M + W;

			// 인접 행렬 초기화
			long[][] adjMatrix = new long[V + 1][V + 1];
			for (int i = 0; i < adjMatrix.length; i++) {
				Arrays.fill(adjMatrix[i], INF);
				adjMatrix[i][i] = 0L;
			}

			// 도로 입력
			for (int road = 0; road < M; road++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				long weight = Long.parseLong(st.nextToken());
				if (weight < adjMatrix[from][to]) {
					adjMatrix[from][to] = weight;
				}
				if (weight < adjMatrix[to][from]) {
					adjMatrix[to][from] = weight;
				}
			}

			// 웜홀 입력
			for (int wormhole = 0; wormhole < W; wormhole++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				long weight = -Long.parseLong(st.nextToken());
				if (weight < adjMatrix[from][to]) {
					adjMatrix[from][to] = weight;
				}
			}

			// adjMatrix에서 존재하는 edge들을 edges 리스트에 저장
			edges.clear();
			for (int from = 1; from <= V; from++) {
				for (int to = 1; to <= V; to++) {
					if (adjMatrix[from][to] != INF) {
						edges.add(new Edge(from, to, adjMatrix[from][to]));
					}
				}
			}

			// 음의 사이클 존재 여부에 따라 출력 스트링빌더에 결과 추가
			if (existNegCycle()) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}

		} // end for testCase

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 음의 사이클 존재 여부를 리턴 */
	public static boolean existNegCycle() {
		boolean[] isVisited = new boolean[V + 1];
		
		for (int start = 1; start <= V; start++) {
			try {
				// 이미 방문한 지점이라면 이 지점부터 시작해 볼 필요가 없음
				if (isVisited[start]) {
					continue;
				}

				// 아직 방문하지 않은 시작 정점들로부터 출발해봄
				long dists[] = bellmanFord(start);
				for (int v = 1; v <= V; v++) {
					if (dists[v] < INF - BIG_NUM) {
						isVisited[v] = true;
					}
				}

			} catch (NegCycleException e) {
				return true;
			}
		}

		return false;
	}

	/**
	 * start에서 다른 지점들까지의 최단 경로의 길이를 리턴
	 * 
	 * @throws NegCycleException
	 */
	public static long[] bellmanFord(int start) throws NegCycleException {
		// dists 초기화
		long[] dists = new long[V + 1];
		Arrays.fill(dists, INF);
		dists[start] = 0L;

		// 벨만 포드 알고리즘으로 음의 사이클 판단
		for (int i = 0; i < V; i++) {
			for (Edge e : edges) {
				if (dists[e.from] < INF - BIG_NUM && dists[e.to] > dists[e.from] + e.weight) {
					dists[e.to] = dists[e.from] + e.weight;
					
					// V번째 루프에서도 거리 갱신이 일어난다면, 음의 사이클이 존재
					if (i == V - 1) {
						throw new NegCycleException();
					}
				}
			}
		}

		return dists;
	}
}