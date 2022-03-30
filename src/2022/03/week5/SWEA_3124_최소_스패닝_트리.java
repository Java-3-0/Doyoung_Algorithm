// 137696KB, 2919ms

package swea_3124;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
	static final int MAX_V = 100000, MAX_E = 200000;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];
	static int V, E;

	static class Edge implements Comparable<Edge> {
		int to;
		long weight;

		public Edge(int to, long weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			if (this.weight < e.weight) {
				return -1;
			} else if (this.weight == e.weight) {
				return 0;
			} else {
				return 1;
			}
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

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// adjList 메모리 클리어
			clearAdjList();

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				long weight = Long.parseLong(st.nextToken());
				adjList[from].add(new Edge(to, weight));
				adjList[to].add(new Edge(from, weight));
			}
	
			// 프림 알고리즘을 통해 mst 길이 계산
			long mst = prim();
			
			// 출력 스트링빌더에 형식에 맞게 결과 추가
			sb.append("#").append(testCase).append(" ").append(mst).append("\n");
			
		}// end for testCase

		// 출력
		System.out.print(sb.toString());

	} // end main

	public static long prim() {
		long ret = 0L;
		
		boolean[] isVisited = new boolean[V + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		int start = 1;
		pq.offer(new Edge(start, 0L));
		
		int cnt = 0;
		while (!pq.isEmpty()) {
			// pq에서 weight가 가장 작은 것 뽑아오기
			Edge here = pq.poll();
			
			// 이미 방문했으면 패스
			if (isVisited[here.to]) {
				continue;
			}
			
			// 방문
			isVisited[here.to]= true; 
			ret += here.weight;
			cnt++;
			if (cnt == V) {
				return ret;
			}

			// 인접한 정점들로의 간선을 pq에 추가
			for (Edge there: adjList[here.to]) {
				if (!isVisited[there.to]) {
					pq.offer(there);
				}
			}
			
		}
		
		// 도달하지 못하는 곳
		return -1;
	}
	
	public static void clearAdjList() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
	}

}