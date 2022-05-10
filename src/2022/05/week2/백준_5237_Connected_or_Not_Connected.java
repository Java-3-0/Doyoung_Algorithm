// 11816KB, 80ms

package bj5237;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 250;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	static int V, E;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();
			
			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				adjList[from].add(to);
				adjList[to].add(from); // 양방향 처리
			}
			
			// dfs 수행
			dfs(1);
			
			// 결과를 출력용 스트링빌더에 담기
			if (visitedAll()) {
				sb.append("Connected.");
			}
			else {
				sb.append("Not connected.");
			}
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 모두 방문했는지 여부를 리턴 */
	public static boolean visitedAll() {
		for (int v = 0; v < V; v++) {
			if (!isVisited[v]) {
				return false;
			}
		}
		
		return true;
	}
	
	/** 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		
		Arrays.fill(isVisited, false);
	}
	
	/** dfs 수행 */
	public static void dfs(int here) {
		if (isVisited[here]) {
			return;
		}
		isVisited[here] = true;

		for (int there : adjList[here]) {
			dfs(there);
		}
	}

}