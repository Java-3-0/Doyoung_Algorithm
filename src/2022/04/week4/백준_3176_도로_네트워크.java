// 119376KB, 800ms

package bj3176;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5, MAX_K = (int) 1e5;
	static final int INF = 987654321;

	static int N, K;
	static int LOG_N;
	static List<Edge>[] adjList;
	static int[] depth;
	static boolean[] isVisited;
	/** 각 노드의 2^k 부모 정보를 저장하는 sparseTable */
	static int[][] parentST;
	/** 각 노드의 2^k 경로 길이를 저장하는 sparseTable */
	static int[][] minWeightST;
	static int[][] maxWeightST;

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

	static class Result {
		int minWeight;
		int maxWeight;

		public Result(int minWeight, int maxWeight) {
			super();
			this.minWeight = minWeight;
			this.maxWeight = maxWeight;
		}

		@Override
		public String toString() {
			return "Result [minWeight=" + minWeight + ", maxWeight=" + maxWeight + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 도시 개수 입력
		N = Integer.parseInt(br.readLine());
		LOG_N = (int) Math.ceil(Math.log(N) / Math.log(2));

		// 전역변수 메모리 할당
		malloc();

		for (int i = 0; i < minWeightST.length; i++) {
			Arrays.fill(minWeightST[i], INF);
		}

		// 간선 입력
		for (int i = 0; i < N - 1; i++) {
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

		// 쿼리 수 입력
		K = Integer.parseInt(br.readLine());

		// 쿼리 처리
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int C = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			Result res = getWeightsToLCA(C, D);
			sb.append(res.minWeight).append(" ").append(res.maxWeight).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** dfs를 수행하면서 각 노드의 깊이를 저장하고, 부모 정보, 올라가는 간선 정보에 대해한 sparse table의 첫 줄도 채운다 */
	public static void dfs(int now) {
		for (Edge e : adjList[now]) {
			int next = e.to;
			int weight = e.weight;
			if (!isVisited[next]) {
				isVisited[next] = true;
				depth[next] = depth[now] + 1;
				parentST[0][next] = now;
				minWeightST[0][next] = weight;
				maxWeightST[0][next] = weight;
				dfs(next);
			}
		}
	}

	/** sparseTable의 첫 줄은 이미 저장되어 있는 상태일 때, 나머지 줄을 계산해서 저장 */
	public static void makeSparseTable() {
		for (int k = 1; k <= LOG_N; k++) {
			for (int num = 1; num <= N; num++) {
				int ancestor = parentST[k - 1][num];
				parentST[k][num] = parentST[k - 1][ancestor];
				minWeightST[k][num] = Math.min(minWeightST[k - 1][num], minWeightST[k - 1][ancestor]);
				maxWeightST[k][num] = Math.max(maxWeightST[k - 1][num], maxWeightST[k - 1][ancestor]);
			}
		}
	}

	/** 전역변수 메모리 할당 */
	public static void malloc() {
		adjList = new ArrayList[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		depth = new int[N + 1];
		isVisited = new boolean[N + 1];
		parentST = new int[LOG_N + 1][N + 1];
		minWeightST = new int[LOG_N + 1][N + 1];
		maxWeightST = new int[LOG_N + 1][N + 1];
	}

	/** 최소 공통 조상까지의 최소 비용 간선, 최대 비용 간선을 구해서 리턴 */
	public static Result getWeightsToLCA(int node1, int node2) {
		if (node1 == node2) {
			return new Result(0, 0);
		}

		int d1 = depth[node1];
		int d2 = depth[node2];

		// node1의 depth <= node2의 depth인 상태를 유지
		if (d1 > d2) {
			return getWeightsToLCA(node2, node1);
		}

		int minW = INF;
		int maxW = 0;

		// node2를 node1과 같은 레벨로 끌어올린다
		int diff = d2 - d1;
		for (int k = LOG_N; k >= 0; k--) {
			if ((diff & (1 << k)) != 0) {
				minW = Math.min(minW, minWeightST[k][node2]);
				maxW = Math.max(maxW, maxWeightST[k][node2]);
				node2 = parentST[k][node2];
			}
		}

		// 여기가 공통 조상이면 결과를 리턴
		if (node1 == node2) {
			return new Result(minW, maxW);
		}

		// 여기가 공통 조상이 아니라면, 큰 점프부터 수행하면서 두 노드가 다른 조상을 가질 때마다 위로 끌어올린다
		for (int k = LOG_N; k >= 0; k--) {
			if (parentST[k][node1] != parentST[k][node2]) {
				minW = Math.min(minW, minWeightST[k][node1]);
				maxW = Math.max(maxW, maxWeightST[k][node1]);

				minW = Math.min(minW, minWeightST[k][node2]);
				maxW = Math.max(maxW, maxWeightST[k][node2]);

				node1 = parentST[k][node1];
				node2 = parentST[k][node2];
			}
		}

		// 이 시점엔 node1과 node2의 부모 노드가 LCA가 된다
		minW = Math.min(minW, minWeightST[0][node1]);
		maxW = Math.max(maxW, maxWeightST[0][node1]);

		minW = Math.min(minW, minWeightST[0][node2]);
		maxW = Math.max(maxW, maxWeightST[0][node2]);

		return new Result(minW, maxW);
	}

}