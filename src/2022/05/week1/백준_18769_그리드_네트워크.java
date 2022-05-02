// 337300KB, 2320ms

package bj18769;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_R = 500, MAX_C = 500;
	static final int MAX_V = MAX_R * MAX_C;
	static final int INF = 987654321;

	static int R, C, T;
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

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// R, C 입력
			st = new StringTokenizer(br.readLine(), " ");
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());

			// 가로 간선 정보 입력
			for (int r = 0; r < R; r++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int c = 0; c < C - 1; c++) {
					int weight = Integer.parseInt(st.nextToken());
					int from = getVertexNum(r, c);
					int to = getVertexNum(r, c + 1);

					adjList[from].add(new Edge(to, weight));
					adjList[to].add(new Edge(from, weight));
				}
			}

			// 세로 간선 정보 입력
			for (int r = 0; r < R - 1; r++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int c = 0; c < C; c++) {
					int weight = Integer.parseInt(st.nextToken());
					int from = getVertexNum(r, c);
					int to = getVertexNum(r + 1, c);

					adjList[from].add(new Edge(to, weight));
					adjList[to].add(new Edge(from, weight));
				}
			}

			// MST 길이 계산
			int mst = getMST();

			// 출력용 스트링빌더에 추가
			sb.append(mst).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
	
	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isSelected, false);
	}

	/** mst 길이를 리턴 */
	public static int getMST() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		// 시작 정점 처리
		int start = 0;
		isSelected[start] = true;
		for (Edge e : adjList[start]) {
			if (!isSelected[e.to]) {
				pq.offer(e);
			}
		}

		// Prim 알고리즘 수행
		int ret = 0;
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge now = pq.poll();
			if (isSelected[now.to]) {
				continue;
			}
			isSelected[now.to] = true;
			ret += now.weight;

			// 연결된 간선들을 pq에 넣는다
			for (Edge next : adjList[now.to]) {
				if (!isSelected[next.to]) {
					pq.offer(next);
				}
			}
		}

		return ret;
	}

	public static int getVertexNum(int r, int c) {
		return C * r + c;
	}
}