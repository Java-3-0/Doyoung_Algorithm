// 17072KB, 144ms

package bj5938;

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

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
			adjList[to].add(from); // 양방향 처리
		}

		// 방문 여부 초기화
		Arrays.fill(isVisited, false);
		
		// dfs 수행
		dfs(1);

		// 방문하지 않은 정점들 출력
		for (int v = 1; v <= V; v++) {
			if (!isVisited[v]) {
				sb.append(v).append("\n");
			}
		}

		if (sb.length() == 0) {
			System.out.println(0);
		}
		else {
			System.out.print(sb.toString());
		}

	} // end main
	
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