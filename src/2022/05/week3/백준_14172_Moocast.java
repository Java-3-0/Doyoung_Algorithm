// 11952KB, 88ms

package bj14172;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200;

	static int V;
	static List<Integer>[] adjList;
	static boolean[] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수 입력
		V = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		adjList = new ArrayList[V];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		isVisited= new boolean[V];

		// 각 정점의 위치와 파워 입력
		long[][] positions = new long[V][2];
		long[] powerSqrs = new long[V];
		for (int i = 0; i < V; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			positions[i][0] = Long.parseLong(st.nextToken());
			positions[i][1] = Long.parseLong(st.nextToken());

			long power = Long.parseLong(st.nextToken());
			powerSqrs[i] = power * power;
		}

		// 위치와 파워 정보를 이용해서 간선 정보 계산
		for (int from = 0; from < V; from++) {
			adjList[from].add(from);
			for (int to = from + 1; to < V; to++) {
				
				long dy = positions[to][1] - positions[from][1];
				long dx = positions[to][0] - positions[from][0];
				long distSqr = dy * dy + dx * dx;
				if (distSqr <= powerSqrs[from]) {
					adjList[from].add(to);
				}
				if (distSqr <= powerSqrs[to]) {
					adjList[to].add(from);
				}
			}
		}
		
		// 각 정점을 시작점으로 하여 dfs로 방문 가능한 정점 개수의 최대치 계산
		int maxCnt = 0;
		for (int v = 0; v < V; v++) {
			Arrays.fill(isVisited, false);
			int cnt = dfs(v);
			maxCnt = Math.max(maxCnt, cnt);
		}

		// 출력
		System.out.println(maxCnt);

	} // end main

	/** now로부터 dfs를 수행하고 방문 정점 개수를 리턴 */
	private static int dfs(int now) {
		if (isVisited[now]) {
			return 0;
		}
		isVisited[now] = true;
		
		int ret = 1;
		for (int next : adjList[now]) {
			ret += dfs(next);
		}

		return ret;
	}
}