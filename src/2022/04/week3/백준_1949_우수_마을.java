// 20704KB, 184ms

package bj1949;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e4;

	static int N;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] populations = new int[MAX_N + 1];

	// dp[i][0] : i번 노드가 우수 마을이 아닐 때, i번 노드를 루트로 하는 서브트리의 최대 우수 마을 인구 수 합
	// dp[i][1] : i번 노드가 우수 마을일 때, i번 노드를 루트로 하는 서브트리의 최대 우수 마을 인구 수 합
	static int[][] dp = new int[MAX_N + 1][2];
	static boolean[] isVisited = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// 마을들의 인구수 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			populations[i] = Integer.parseInt(st.nextToken());
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

		// 우수 마을 인구수 합의 최대치 계산
		int answer = Math.max(dp[1][0], dp[1][1]);

		// 출력
		System.out.println(answer);

	} // end main

	/** dfs를 수행하면서 트리 dp */
	public static void dfs(int start) {
		isVisited[start] = true;

		dp[start][0] = 0;
		dp[start][1] = populations[start];
		
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