// 20728KB, 180ms

package bj1967;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000;
	static final int ROOT = 1;

	static int N;
	static boolean[] isVisited = new boolean[MAX_N + 1];
	static ArrayList<Edge>[] adjList = new ArrayList[MAX_N + 1];
	static int farthestVertex;
	static int maxDist = Integer.MIN_VALUE;

	static class Edge {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 인접 리스트 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// 노드 개수 입력
		N = Integer.parseInt(br.readLine());

		// 트리 입력
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int parent = Integer.parseInt(st.nextToken());
			int child = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList[parent].add(new Edge(child, weight));
			adjList[child].add(new Edge(parent, weight));
		}

		// 루트에서부터 dfs수행하면서 가장 먼 노드까지의 거리와 그 노드를 구한다.
		dfs(ROOT, 0);

		// 가장 먼 노드에서부터 다시 dfs를 수행하면서 최장 거리가 갱신된다면 갱신한다.
		Arrays.fill(isVisited, false);
		dfs(farthestVertex, 0);

		// 구한 최장 거리가 지름이므로 출력한다.
		System.out.println(maxDist);

	} // end main

	/** dfs를 수행하면서 가장 먼 정점과 그 거리를 갱신한다 */
	public static void dfs(int here, int accum) {
		if (isVisited[here]) {
			return;
		}

		isVisited[here] = true;

		for (Edge e : adjList[here]) {
			dfs(e.to, accum + e.weight);
		}

		if (maxDist < accum) {
			maxDist = accum;
			farthestVertex = here;
		}

		isVisited[here] = false;
		return;
	}
}