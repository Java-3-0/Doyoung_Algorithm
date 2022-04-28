// 165112KB, 1424ms

package bj17396;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000;
	static final int MAX_E = 300000;

	static int V;
	static int E;
	static List<Edge>[] adjList = new ArrayList[MAX_V];
	static final boolean[] isSeen = new boolean[MAX_V];

	static class Edge {
		int to;
		long weight;

		public Edge(int to, long weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

	}

	static class Vertex implements Comparable<Vertex> {
		int vNum;
		long distance;

		public Vertex(int vNum, long distance) {
			super();
			this.vNum = vNum;
			this.distance = distance;
		}

		@Override
		public int compareTo(Vertex v) {
			return Long.compare(this.distance, v.distance);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 각 정점의 시야 여부 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < V; i++) {
			int input = Integer.parseInt(st.nextToken());
			isSeen[i] = input == 1 ? true : false;
		}

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			long weight = Long.parseLong(st.nextToken());

			// 양방향으로 처리
			adjList[a].add(new Edge(b, weight));
			adjList[b].add(new Edge(a, weight));
		}

		long answer = dijkstra(0);

		System.out.println(answer);

	} // end main

	public static long dijkstra(int start) {
		boolean[] isVisited = new boolean[V];
		PriorityQueue<Vertex> pq = new PriorityQueue<>();

		// 시작 정점 처리
		pq.offer(new Vertex(start, 0));

		// 다익스트라 알고리즘 수행
		while (!pq.isEmpty()) {
			Vertex now = pq.poll();

			if (isVisited[now.vNum]) {
				continue;
			}

			isVisited[now.vNum] = true;
			if (now.vNum == V - 1) {
				return now.distance;
			}

			for (Edge e : adjList[now.vNum]) {
				if (!isVisited[e.to] && canGo(e.to)) {
					pq.offer(new Vertex(e.to, now.distance + e.weight));
				}
			}
		}

		return -1L;
	}

	/** v번 정점으로 안 들키고 갈 수 있는지 여부를 리턴 */
	public static boolean canGo(int v) {
		// 안 보이는 중간 정점들과, 보이는 상대 넥서스는 이동 가능
		if (!isSeen[v] || v == V - 1) {
			return true;
		}

		return false;
	}

}