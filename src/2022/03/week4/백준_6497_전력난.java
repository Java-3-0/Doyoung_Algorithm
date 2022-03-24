// 302260KB, 1380ms

package bj6497;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200000, MAX_E = 200000;

	static int V, E;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

	static class Edge implements Comparable<Edge> {
		int vNum;
		int weight;

		public Edge(int vNum, int weight) {
			super();
			this.vNum = vNum;
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

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		while (true) {
			// adjList 메모리 초기화
			for (int i = 0; i < adjList.length; i++) {
				adjList[i].clear();
			}

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 0, 0이 입력되면 입력 종료
			if (V == 0 && E == 0) {
				break;
			}

			// 간선 정보 입력
			int sumWeight = 0;
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());

				adjList[from].add(new Edge(to, weight));
				adjList[to].add(new Edge(from, weight));
				sumWeight += weight;
			}

			int saveMoney = sumWeight - prim();
			sb.append(saveMoney).append("\n");
		}

		System.out.print(sb.toString());
		
	} // end main

	/** 프림 알고리즘을 이용하여 mst 길이 계산해서 리턴 */
	public static int prim() {
		int ret = 0;
		boolean[] isVisited = new boolean[V + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		pq.offer(new Edge(1, 0));
		
		int cnt = 0;
		while (!pq.isEmpty()) {
			Edge here = pq.poll();
			if (isVisited[here.vNum]) {
				continue;
			}
			
			isVisited[here.vNum] = true;
			ret += here.weight;
			cnt++;
			if (cnt == V) {
				return ret;
			}
			
			for (Edge there: adjList[here.vNum]) {
				if (!isVisited[there.vNum]) {
					pq.offer(there);
				}
			}
			
		}
		
		return ret;
	}

}