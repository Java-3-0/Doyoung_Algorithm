// 20740KB, 236ms

package bj2213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e4;

	static int N;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] weights = new int[MAX_N + 1];
	static List<Integer> includedNodes = new ArrayList<>();

	// dp[i][0] : i번 노드가 독립 집합에 포함되지 않을 때, i번 노드를 루트로 하는 서브트리의 최대 독립 집합 크기
	// dp[i][1] : i번 노드가 독립 집합에 포함될 때, i번 노드를 루트로 하는 서브트리의 최대 독립 집합 크기
	static int[][] dp = new int[MAX_N + 1][2];
	static boolean[] isVisited = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// 마을들의 인구수 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			weights[i] = Integer.parseInt(st.nextToken());
		}

		// 간선 정보 입력
		for (int e = 0; e < N - 1; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());
			adjList[node1].add(node2);
			adjList[node2].add(node1);
		}

		// 트리 dp 수행
		dfs(1);

		// 방문 여부 초기화
		initIsVisited();

		// 독립 집합에 속하는 정점들 찾기
		retrieve(1, false);

		// 독립 집합 크기를 출력 스트링빌더에 담는다
		int setSize = Math.max(dp[1][0], dp[1][1]);
		sb.append(setSize).append("\n");

		// 정점들 오름차순 정렬
		Collections.sort(includedNodes);
		
		// 독립 집합에 속하는 정점들을 출력 스트링빌더에 담는다
		for (int node : includedNodes) {
			sb.append(node).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

	public static void retrieve(int start, boolean isParentUsed) {
		isVisited[start] = true;

		if (!isParentUsed && dp[start][0] <= dp[start][1]) {
			includedNodes.add(start);
			for (int next : adjList[start]) {
				if (!isVisited[next]) {
					retrieve(next, true);
				}
			}
		}

		else {
			for (int next : adjList[start]) {
				if (!isVisited[next]) {
					retrieve(next, false);
				}
			}
		}
	}

	public static void initIsVisited() {
		Arrays.fill(isVisited, false);
	}

	/** dfs를 수행하면서 트리 dp */
	public static void dfs(int start) {
		isVisited[start] = true;

		dp[start][0] = 0;
		dp[start][1] = weights[start];

		for (int next : adjList[start]) {
			if (!isVisited[next]) {
				dfs(next);
				dp[start][0] += Math.max(dp[next][0], dp[next][1]);
				dp[start][1] += dp[next][0];
			}
		}

		return;
	}

}