// 135104KB, 964ms

package bj13905;

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
	static final int INF = 987654321;

	static int V, E;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

	static class Edge implements Comparable<Edge> {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		/** 간선 weight의 내림차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Edge e) {
			return -(this.weight - e.weight);
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

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 시작 위치, 끝 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			// 양방향
			adjList[from].add(new Edge(to, weight));
			adjList[to].add(new Edge(from, weight));
		}

		int answer = getMaxPepero(start, end);
		
		System.out.println(answer);

	} // end main

	/** prim 알고리즘을 이용하여 시작 지점으로부터 끝 점까지 가지고 갈 수 있는 최대 빼빼로 무게를 리턴 */
	public static int getMaxPepero(int start, int end) {
		boolean[] isVisited = new boolean[V + 1];
		int ret = INF;
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.offer(new Edge(start, INF));
		
		while (!pq.isEmpty()) {
			// 가장 weight가 큰 간선을 택한다.
			Edge here = pq.poll();
			
			// 아직 방문하지 않았다면 방문한다.
			if (isVisited[here.to]) {
				continue;
			}
			isVisited[here.to] = true;
			
			// 무게 제한 갱신
			ret = Math.min(ret, here.weight);
			
			// 도착 지점에 도달했다면 종료
			if (here.to == end) {
				return ret;
			}
			
			// 이 정점에서 아직 방문하지 않은 정점으로 연결된 간선들을 pq에 넣는다.
			for (Edge there : adjList[here.to]) {
				if (!isVisited[there.to]) {
					pq.offer(there);
				}
			}
		}
		
		return 0;
	}

}