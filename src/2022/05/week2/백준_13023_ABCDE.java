// 17300KB, 236ms

package bj13023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 2000, MAX_E = 2000;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V];
	static boolean[] isVisited = new boolean[MAX_V];

	static int maxDepth = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

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
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			adjList[a].add(b);
			adjList[b].add(a);
		}

		// 루트부터 dfs 수행
		for (int v = 0; v < V; v++) {
			Arrays.fill(isVisited, false);
			isVisited[v] = true;
			if (dfs(v, 0)) {
				break;
			}
		}

		// 깊이가 4 이상으로 들어가는 경우가 있으면 문제에서 주어진 친구 관계가 존재
		if (maxDepth >= 4) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}

	} // end main

	/** here로부터 dfs를 수행, 4이상의 깊이를 찾으면 true를 리턴 */
	public static boolean dfs(int here, int depth) {
		maxDepth = Math.max(maxDepth, depth);
		if (maxDepth >= 4) {
			return true;
		}

		for (int next : adjList[here]) {
			if (!isVisited[next]) {
				isVisited[next] = true;
				if (dfs(next, depth + 1)) {
					return true;
				}
				isVisited[next] = false;
			}
		}

		return false;
	}
}