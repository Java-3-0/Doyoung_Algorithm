// 51244KB, 452ms

package bj1916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언
	static final int MAX_V = 1000, MAX_E = 100000;
	static final int MAX_WEIGHT = 99999;
	static final int INF = 987654321;
	
	// 전역변수 선언
	static int V, E;
	static List<Edge> adjList[];
	
	/** 인접 리스트에 넣을 간선 객체 */
	static class Edge {
		/** 간선이 향하는 정점 */
		int to;
		/** 간선의 가중치 */
		int weight;
		
		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}
	
	// 가장 가까운 정점부터 앞에 오는 우선순위 큐에 넣을 정점 객체
	static class Vertex implements Comparable<Vertex>{
		/** 정점의 번호 */
		int no;
		/** 이미 선택된 정점에서 이 정점으로 오는 최단 거리 */
		int minDistance;
		
		public Vertex(int no, int minDistance) {
			super();
			this.no = no;
			this.minDistance = minDistance;
		}

		/** 거리 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Vertex v) {
			return this.minDistance - v.minDistance;
		}
	}
	
	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 정점 수, 간선 수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());
		
		// 인접 리스트 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}
		
		// 간선 정보 입력받아서 인접 리스트 형태로 저장
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			adjList[from].add(new Edge(to, weight));
		}
		
		// 경로 시작 지점, 끝 지점 입력
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		// 시작 지점부터 끝 지점까지의 거리를 우선순위 큐를 사용한 다익스트라 알고리즘으로 계산
		int distance = dijkstra(start, end);

		// 출력
		System.out.println(distance);
		
	} // end main

	/** 우선순위 큐를 사용한 다익스트라 알고리즘을 통해 start 위치에서부터 모든 정점까지의 최단 경로를 각각 구해서 배열에 담아 리턴한다 */
	public static int dijkstra(int start, int end) {
		boolean[] isSelected = new boolean[V + 1];
		int[] distances = new int[V + 1];
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		
		// 모든 정점까지의 최단 거리를 INF로 초기화
		Arrays.fill(distances, INF);
		
		
		// 시작 정점을 선택하고, 모든 정점까지의 최단 거리를 시작 정점에서 바로 오는 거리로 초기화
		distances[start] = 0;
		pq.offer(new Vertex(start, 0));
		for (Edge e : adjList[start]) {
			distances[e.to] = Math.min(distances[e.to], e.weight);
		}
		
		while (!pq.isEmpty()) {
			// 가장 가까운 정점을 pq에서 뽑아옴
			Vertex current = pq.poll();
			
			// 이미 선택된 정점이면 패스
			if (isSelected[current.no]) {
				continue;
			}
			
			// 아직 선택되지 않은 정점이면 선택
			isSelected[current.no] = true;
			
			if (current.no == end) {
				return distances[end];
			}
			
			// 이 정점으로부터 연결된 아직 선택되지 않은 정점들까지의 최단 경로를 업데이트
			for (Edge e : adjList[current.no]) {
				if (!isSelected[e.to]) {
					distances[e.to] = Math.min(distances[e.to], distances[current.no] + e.weight); 
					pq.offer(new Vertex(e.to, distances[e.to]));
				}
			}
		}
		
		return distances[end];
	}
}