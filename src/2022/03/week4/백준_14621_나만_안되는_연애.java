// 17780KB, 192ms

package bj14621;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int V, E;
	static List<Edge>[] adjList;

	static class Edge implements Comparable<Edge> {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 각 대학교가 남초인지 여부 입력
		boolean[] isMale = new boolean[V + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int v = 1; v <= V; v++) {
			if (st.nextToken().charAt(0) == 'M') {
				isMale[v] = true;
			}
		}

		// adjList 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			// 남초와 여초를 잇는 간선만 등록
			if (isMale[from] != isMale[to]) {
				adjList[from].add(new Edge(to, weight));
				adjList[to].add(new Edge(from, weight));
			}
		}

		int answer = prim();

		System.out.println(answer);
		
	} // end main

	public static int prim() {
		int ret = 0;

		boolean[] isVisited = new boolean[V + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.offer(new Edge(1, 0));

		int cnt = 0;
		while (!pq.isEmpty()) {
			Edge here = pq.poll();
			if (isVisited[here.to]) {
				continue;
			}

			isVisited[here.to] = true;
			ret += here.weight;
			cnt++;
			if (cnt == V) {
				return ret;
			}

			for (Edge there : adjList[here.to]) {
				pq.offer(there);
			}
		}

		return -1;
	}
}