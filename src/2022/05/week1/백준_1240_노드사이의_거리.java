// 12680KB, 104ms

package bj1240;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000, MAX_Q = 1000;

	static int V, Q;
	static int LOG_V;
	static List<Edge>[] adjList;
	static int[] depth;
	static boolean[] isVisited;
	/** 각 노드의 2^k 부모 정보를 저장하는 sparseTable */
	static int[][] parentST;
	/** 각 노드의 2^k 경로 길이를 저장하는 sparseTable */
	static int[][] distST;

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

		// 정점 개수, 쿼리 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		LOG_V = (int) Math.ceil(Math.log(V) / Math.log(2));

		// 전역변수 메모리 할당
		malloc();

		for (int i = 0; i < distST.length; i++) {
			Arrays.fill(distST[i], 0);
		}

		// 간선 입력
		for (int i = 0; i < V - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			adjList[A].add(new Edge(B, weight));
			adjList[B].add(new Edge(A, weight));
		}

		// 트리 생성
		isVisited[1] = true;
		depth[1] = 1;
		dfs(1);

		// sparseTable을 구한다
		makeSparseTable();

		// 쿼리 처리
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int C = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			int answer = getDistance(C, D);
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** dfs를 수행하면서 각 노드의 깊이를 저장하고, parentST, distST의 첫 줄도 채운다 */
	public static void dfs(int now) {
		for (Edge e : adjList[now]) {
			int next = e.to;
			int weight = e.weight;
			if (!isVisited[next]) {
				isVisited[next] = true;
				depth[next] = depth[now] + 1;
				parentST[0][next] = now;
				distST[0][next] = weight;
				dfs(next);
			}
		}
	}

	/** sparseTable의 첫 줄은 이미 저장되어 있는 상태일 때, 나머지 줄을 계산해서 저장 */
	public static void makeSparseTable() {
		for (int k = 1; k <= LOG_V; k++) {
			for (int num = 1; num <= V; num++) {
				int ancestor = parentST[k - 1][num];
				parentST[k][num] = parentST[k - 1][ancestor];
				distST[k][num] = distST[k - 1][num] + distST[k - 1][ancestor];
			}
		}
	}

	/** 전역변수 메모리 할당 */
	public static void malloc() {
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		depth = new int[V + 1];
		isVisited = new boolean[V + 1];
		parentST = new int[LOG_V + 1][V + 1];
		distST = new int[LOG_V + 1][V + 1];
	}

	/** 최소 공통 조상까지의 경로 길이을 구해서 리턴 */
	public static int getDistance(int node1, int node2) {
		if (node1 == node2) {
			return 0;
		}

		int d1 = depth[node1];
		int d2 = depth[node2];

		// node1의 depth <= node2의 depth인 상태를 유지
		if (d1 > d2) {
			return getDistance(node2, node1);
		}

		int dist = 0;

		// node2를 node1과 같은 레벨로 끌어올린다
		int diff = d2 - d1;
		for (int k = LOG_V; k >= 0; k--) {
			if ((diff & (1 << k)) != 0) {
				dist += distST[k][node2];
				node2 = parentST[k][node2];
			}
		}

		// 여기가 공통 조상이면 결과를 리턴
		if (node1 == node2) {
			return dist;
		}

		// 여기가 공통 조상이 아니라면, 큰 점프부터 수행하면서 두 노드가 다른 조상을 가질 때마다 위로 끌어올린다
		for (int k = LOG_V; k >= 0; k--) {
			if (parentST[k][node1] != parentST[k][node2]) {
				dist += distST[k][node1];
				dist += distST[k][node2];

				node1 = parentST[k][node1];
				node2 = parentST[k][node2];
			}
		}

		// 이 시점엔 node1과 node2의 부모 노드가 LCA가 된다
		dist += distST[0][node1];
		dist += distST[0][node2];

		return dist;
	}

}