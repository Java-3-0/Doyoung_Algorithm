// 31756KB, 292ms

package bj12784;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int INF = 987654321;

	static int V, E;
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
			initMemory();

			// 정점 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int e = 0; e < V - 1; e++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());
				adjList[from].add(new Edge(to, weight));
				adjList[to].add(new Edge(from, weight));
			}

			// 우수 마을 인구수 합의 최대치 계산
			int answer = 0;
			if (V != 1) {
				answer = dfs(1, INF);
			}

			// 출력 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isVisited, false);
	}

	/** dfs를 수행하면서 트리 dp */
	public static int dfs(int start, int weight) {

		// 이 노드로 내려오는 간선을 끊는 경우
		isVisited[start] = true;
		int ret = weight;

		// 이 노드의 자식 노드를 모두 끊는 경우
		int sum = 0;
		for (Edge next : adjList[start]) {
			if (!isVisited[next.to]) {
				sum += dfs(next.to, next.weight);
			}
		}

		// 리프 노드가 아닌 경우에만 자식 노드를 끊는 선택을 할 수 있다
		if (sum != 0) {
			ret = Math.min(ret, sum);
		}

		return ret;
	}

}