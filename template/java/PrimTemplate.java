import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PrimTemplate {
	static final int MAX_V = 10000, MAX_E = 30000;
	static final int INF = 987654321;
	static final int FAIL = -1;

	static int V, E;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[] isSelected = new boolean[MAX_V + 1];

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
			return Integer.compare(this.weight, e.weight);
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
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			// 양방향 처리
			adjList[x].add(new Edge(y, weight));
			adjList[y].add(new Edge(x, weight));
		}

		// MST 길이 계산
		int mst = getMST();

		// 출력
		int answer = mst;
		System.out.print(answer);

	} // end main

	/** mst 길이를 리턴 */
	public static int getMST() {
		Arrays.fill(isSelected, false);
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		// 시작 정점 처리
		int start = 1;
		isSelected[start] = true;
		for (Edge e : adjList[start]) {
			if (!isSelected[e.to]) {
				pq.offer(e);
			}
		}

		// MST에 포함하게 된 간선 개수, MST 길이
		int cnt = 0;
		int ret = 0;

		// Prim 알고리즘 수행
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge now = pq.poll();
			if (isSelected[now.to]) {
				continue;
			}
			isSelected[now.to] = true;
			ret += now.weight;
			cnt++;

			// 연결된 간선들을 pq에 넣는다
			for (Edge next : adjList[now.to]) {
				if (!isSelected[next.to]) {
					pq.offer(next);
				}
			}
		}

		// MST가 완성된 경우 그 길이를 리턴
		if (cnt == V - 1) {
			return ret;
		}

		// 이외의 경우 FAIL 리턴
		else {
			return FAIL;
		}
	}

}