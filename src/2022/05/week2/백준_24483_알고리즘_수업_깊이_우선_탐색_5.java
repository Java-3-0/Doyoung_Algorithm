// 88328KB, 884ms

package bj24483;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000;
	static final int MAX_E = 200000;
	static final int NOT_VISITED = -1;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] depths = new int[MAX_V + 1];
	static int[] visitOrders = new int[MAX_V + 1];
	static int V, E, R;
	static int visitCnt = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수, 간선 수, 시작 정점 번호 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
			adjList[to].add(from); // 양방향 처리
		}

		// 간선 정보 오름차순 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}

		// 방문 여부 초기화
		Arrays.fill(depths, NOT_VISITED);
		Arrays.fill(visitOrders, 0);

		// dfs 수행
		dfs(R, 0);

		// 결과 출력
		long answer = 0L;
		for (int v = 1; v <= V; v++) {
			answer += (long) depths[v] * (long) visitOrders[v];
		}
		System.out.println(answer);

	} // end main

	/** dfs 수행 */
	public static void dfs(int here, int depth) {
		if (depths[here] != NOT_VISITED) {
			return;
		}
		depths[here] = depth;
		visitOrders[here] = ++visitCnt;

		for (int there : adjList[here]) {
			dfs(there, depth + 1);
		}
	}

}