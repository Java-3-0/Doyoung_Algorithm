// 17504KB, 184ms

package bj1238;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// X -> 집으로 가는 최단 경로를 구한다.
// 집 -> X로 가는 경로는 모든 도로의 from, to를 뒤집은 후 X -> 집으로 가는 경로를 구한다.

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_E = 10000;
	static final int INF = 987654321;

	static int V, E;
	static int X;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];
	static List<Edge>[] adjListRev = new ArrayList[MAX_V + 1];

	static class Edge {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}

	static class Vertex implements Comparable<Vertex> {
		int vertexNum;
		int distanceToHere;

		public Vertex(int vertexNum, int distanceToHere) {
			super();
			this.vertexNum = vertexNum;
			this.distanceToHere = distanceToHere;
		}

		@Override
		public int compareTo(Vertex v) {
			return this.distanceToHere - v.distanceToHere;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 인접 리스트 메모리 할당
		mallocAdjList();

		// 정점 수, 간선 수, 파티 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		// 간선 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList[from].add(new Edge(to, weight));
			adjListRev[to].add(new Edge(from, weight));
		}

		// 다익스트라 알고리즘으로 최단 경로
		int[] dists = dijkstra(X, adjList);
		int[] distsRev = dijkstra(X, adjListRev);

		// 모든 정점에서 X로의 왕복 경로 중 최대 거리 계산
		int maxDist = 0;
		for (int i = 1; i <= V; i++) {
			int dist = dists[i] + distsRev[i];
			if (maxDist < dist) {
				maxDist = dist;
			}
		}

		// 출력
		System.out.println(maxDist);

	} // end main

	/** 인접 리스트 메모리 할당 */
	public static void mallocAdjList() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}
		for (int i = 0; i < adjListRev.length; i++) {
			adjListRev[i] = new ArrayList<Edge>();
		}
	}

	/** 인접리스트가 주어졌을 때, 다익스트라 알고리즘으로 start 정점부터 다른 정점들로의 최단 경로 길이를 리턴 */
	public static int[] dijkstra(int start, List<Edge>[] adjList) {
		int[] dists = new int[V + 1];
		Arrays.fill(dists, INF);

		boolean[] isVisited = new boolean[V + 1];

		// 시작 정점 처리
		dists[start] = 0;
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		pq.offer(new Vertex(start, dists[start]));

		// pq가 빌 때까지 수행
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();

			// 이미 방문한 정점이면 패스
			if (isVisited[v.vertexNum]) {
				continue;
			}

			// 방문하고 이 정점까지의 거리를 dists에 담는다.
			isVisited[v.vertexNum] = true;
			dists[v.vertexNum] = v.distanceToHere;
	
			// 이 정점으로부터 갈 수 있는 다른 정점들까지의 거리가 갱신된다면 pq에 넣는다.
			for (Edge e : adjList[v.vertexNum]) {
				if (!isVisited[e.to]) {
					if (dists[e.to] > dists[v.vertexNum] + e.weight) {
						pq.offer(new Vertex(e.to, dists[v.vertexNum] + e.weight));
					}
				}
			}
		}

		return dists;
	}

}