// 53284KB, 504ms

package bj11779;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_E = 100000;
	static final int INF = 987654321;

	static int V, E;
	static ArrayList<Edge>[] adjList;

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
		int minDist;

		public Vertex(int vertexNum, int minDist) {
			super();
			this.vertexNum = vertexNum;
			this.minDist = minDist;
		}

		@Override
		public int compareTo(Vertex v) {
			return this.minDist - v.minDist;
		}
	}
	
	static class Result {
		int minDist;
		List<Integer> route;
		
		public Result(int minDist, List<Integer> route) {
			super();
			this.minDist = minDist;
			this.route = route;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 개수, 간선 개수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// 인접 리스트 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 간선 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList[from].add(new Edge(to, weight));
		}

		// 출발점, 도착점 입력
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		Result r = dijkstra(start, end);

		System.out.println(r.minDist);
		System.out.println(r.route.size());
		for (int num : r.route) {
			System.out.print(num + " ");
		}
		System.out.println();

	} // end main

	public static Result dijkstra(int start, int end) {
		// dists 초기화
		int[] dists = new int[V + 1];
		Arrays.fill(dists, INF);
		boolean[] isVisited = new boolean[V + 1];
		int[] cameFrom = new int[V + 1];

		// 우선순위 큐
		PriorityQueue<Vertex> pq = new PriorityQueue<>();

		// 시작 정점 처리
		dists[start] = 0;
		pq.offer(new Vertex(start, 0));
		cameFrom[start] = -1;

		while (!pq.isEmpty()) {
			Vertex v = pq.poll();

			// 이미 방문한 정점이면 패스
			if (isVisited[v.vertexNum]) {
				continue;
			}

			// 방문
			isVisited[v.vertexNum] = true;

			// 이 정점과 연결된 정점들까지의 최단 경로 갱신
			for (Edge e : adjList[v.vertexNum]) {
				if (!isVisited[e.to] && dists[e.to] > dists[v.vertexNum] + e.weight) {
					dists[e.to] = dists[v.vertexNum] + e.weight;
					cameFrom[e.to] = v.vertexNum;
					pq.offer(new Vertex(e.to, dists[e.to]));
				}
			}
		}

		// 방문하기까지의 루트 계산
		List<Integer> route = new ArrayList<>();
		int here = end;
		do {
			route.add(0, here);
			here = cameFrom[here];
		} while (here != -1);
		
		return new Result(dists[end], route);
	}
}