import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class DijkstraTemplate {
	static final int MAX_V = 5000;
	static final int MAX_E = 10000;

	static int V, E;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

	/** 간선 객체 */
	static class Edge {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

	}

	/** 다익스트라 알고리즘 우선순위큐에 넣기 위한 정점 객체 */
	static class Vertex implements Comparable<Vertex> {
		int vNum;
		int distance;

		public Vertex(int vNum, int distance) {
			super();
			this.vNum = vNum;
			this.distance = distance;
		}

		@Override
		public int compareTo(Vertex v) {
			return Integer.compare(this.distance, v.distance);
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

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			// 양방향으로 처리
			adjList[a].add(new Edge(b, weight));
			adjList[b].add(new Edge(a, weight));
		}

		// 시작 정점, 끝 정점
		int start = 1;
		int end = V;

		// 거리 계산
		int answer = dijkstra(start, end);
		
		// 출력
		System.out.println(answer);

	} // end main

	/** 다익스트라 알고리즘으로 start에서부터 end까지의 최소 비용을 리턴 */
	public static int dijkstra(int start, int end) {
		boolean[] isVisited = new boolean[V + 1];
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
			if (now.vNum == end) {
				return now.distance;
			}

			for (Edge e : adjList[now.vNum]) {
				if (!isVisited[e.to]) {
					pq.offer(new Vertex(e.to, now.distance + e.weight));
				}
			}
		}

		return -1;
	}
}