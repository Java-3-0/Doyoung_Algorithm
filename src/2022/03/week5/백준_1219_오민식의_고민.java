// 11944KB, 88ms

package bj1219;

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
	static final int MAX_V = 100, MAX_E = 100;
	static final long INF = 123456789123456789L;
	static final long UNREACHABLE = -INF;

	static int V, E;
	static long[] moneyToGet;
	static List<Edge>[] adjList;
	static boolean canMakeInfiniteMoney = false;

	static class Edge {
		int to;
		long weight;

		public Edge(int to, long weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge [to=" + to + ", weight=" + weight + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수, 시작 정점, 도착 정점, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		adjList = new ArrayList[V];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}
		moneyToGet = new long[V];

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			long weight = -Long.parseLong(st.nextToken());
			adjList[from].add(new Edge(to, weight));
		}

		// 각 도시에서 벌 수 있는 돈 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < V; i++) {
			moneyToGet[i] = Long.parseLong(st.nextToken());
		}

		// 도시에서 버는 돈을 이용하여 간선 비용 갱신
		for (int v = 0; v < V; v++) {
			for (Edge e : adjList[v]) {
				e.weight += moneyToGet[e.to];
			}
		}

		// 벨만 포드 알고리즘을 이용하여 거리 계산
		long[] dists = bellmanFord(start, end);

		long startMoney = moneyToGet[start];
		long earningMoney = dists[end];

		// 출력
		if (earningMoney == UNREACHABLE) {
			System.out.println("gg");
		} else {
			if (canMakeInfiniteMoney) {
				System.out.println("Gee");
			} else {
				System.out.println(startMoney + earningMoney);
			}
		}

	} // end main

	/** 벨만 포드 알고리즘을 이용하여 최소 경로 길이 계산 */
	public static long[] bellmanFord(int start, int end) {
		long[] dists = new long[V];
		Arrays.fill(dists, UNREACHABLE);

		dists[start] = 0L;
		for (int i = 0; i < V; i++) {
			// 모든 간선에 대해 dists 업데이트
			for (int from = 0; from < V; from++) {
				for (Edge e : adjList[from]) {
					int to = e.to;
					long weight = e.weight;

					if (dists[from] != UNREACHABLE && dists[to] < dists[from] + weight) {
						dists[to] = dists[from] + weight;

						if (i == V - 1) {
							if (canGo(to, end)) {
								canMakeInfiniteMoney = true;
							}
						}
					}
				}
			}

		}

		return dists;
	}

	/** start부터 end까지 갈 수 있는지 여부를 bfs로 파악해서 리턴 */
	public static boolean canGo(int start, int end) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] isVisited = new boolean[V + 1];

		isVisited[start] = true;
		q.offer(start);

		while (!q.isEmpty()) {
			int now = q.poll();
			if (now == end) {
				return true;
			}

			for (Edge e : adjList[now]) {
				int next = e.to;
				if (!isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}
			}
		}

		return false;
	}
}