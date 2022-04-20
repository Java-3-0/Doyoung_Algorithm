// 305924KB, 1224ms

package bj9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 2000, MAX_E = 50000, MAX_T = 100;
	static final int INF = (int) (2 * 1e9);

	static int V, E, T;
	static int S, G, H;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	
	static class Edge {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge [to=" + to + ", weight=" + weight + "]";
		}
		

	}

	static class Vertex implements Comparable<Vertex> {
		int vNum;
		int dist;

		public Vertex(int vNum, int dist) {
			super();
			this.vNum = vNum;
			this.dist = dist;
		}

		@Override
		public int compareTo(Vertex v) {
			if (this.dist < v.dist) {
				return -1;
			}
			else if (this.dist == v.dist) {
				return 0;
			}
			else {
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

		for (int tc = 1; tc <= TESTS; tc++) {
			// adjList 초기화
			initAdjList();
			
			// 정점 수, 간선 수, 목적지 후보 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			T = Integer.parseInt(st.nextToken());
			
			// 출발 정점, 거쳐야 하는 간선의 양 끝 정점들 입력
			st = new StringTokenizer(br.readLine(), " ");
			S = Integer.parseInt(st.nextToken());
			G = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken()) * 2;
				
				if ((from == G && to == H) || (from == H && to == G)) {
					weight--;
				}
				
				// 양방향 처리
				adjList[from].add(new Edge(to, weight));
				adjList[to].add(new Edge(from, weight));
			}
			
			// 정답 계산
			List<Integer> answers = new ArrayList<>();
			for (int i = 0; i < T; i++) {
				int X = Integer.parseInt(br.readLine());
				int dist = dijkstra(S, X);
				if (dist % 2 != 0) {
					answers.add(X);
				}
			}

			// 오름차순 정렬
			Collections.sort(answers);
			
			// 출력 스트링빌더에 담기
			for (int answer : answers) {
				sb.append(answer).append(" ");
			}
			
			sb.append("\n");
			
		} // end for tc

		// 출력
		System.out.print(sb.toString());
		
	} // end main
	
	/** start에서 end까지 최단 경로를 리턴 */
	private static int dijkstra(int start, int end) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		initIsVisited();
		
		pq.offer(new Vertex(start, 0));
		
		while (!pq.isEmpty()) {
			Vertex now = pq.poll();
			if (isVisited[now.vNum]) {
				continue;
			}
			
			isVisited[now.vNum] = true;
			if (now.vNum == end) {
				return now.dist;
			}
			
			for (Edge e : adjList[now.vNum]) {
				if (!isVisited[e.to]) {
					pq.offer(new Vertex(e.to, now.dist + e.weight));
				}
			}
		}
		
		return INF;
	}
	
	/** isVisited 초기화 */
	public static void initIsVisited() {
		Arrays.fill(isVisited, false);
	}

	/** adjList 초기화 */
	public static void initAdjList() {
		for (List<Edge> list : adjList) {
			list.clear();
		}
	}

}