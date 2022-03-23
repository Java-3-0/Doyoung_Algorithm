// 51680KB, 480ms

package bj1922;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_E = 100000;

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
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// adjList 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 간선 입력받아서 adjList에 저장
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			// 양방향
			adjList[from].add(new Edge(to, weight));
			adjList[to].add(new Edge(from, weight));
		}

		// MST 계산
		int mst = prim();

		// 출력
		System.out.println(mst);
	}

	/** 프림 알고리즘을 이용하여 MST의 길이를 계산해서 리턴 */
	public static int prim() {
		int ret = 0;
		int cnt = 0;

		boolean[] isVisited = new boolean[V + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		pq.offer(new Edge(1, 0));

		while (!pq.isEmpty()) {
			Edge here = pq.poll();
			if (isVisited[here.to]) {
				continue;
			}

			isVisited[here.to] = true;
			ret += here.weight;
			cnt++;
			if (cnt == V) {
				break;
			}

			for (Edge there : adjList[here.to]) {
				pq.offer(there);
			}
		}

		return ret;
	}

}
