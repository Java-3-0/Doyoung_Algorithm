// 68268KB, 596ms

package bj16964;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200;
	static final int NOT_VISITED = -1;

	static int V;
	static List<Integer>[] adjList;
	static int[] visitOrderTarget; // 목표하는 방문 순서
	static int[] visitOrder; // 실제 방문 순서
	static int visitCnt = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수 입력
		V = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		visitOrderTarget = new int[V + 1];
		visitOrder = new int[V + 1];

		// 간선 정보 입력
		for (int i = 0; i < V - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int to = Integer.parseInt(st.nextToken());
			int from = Integer.parseInt(st.nextToken());

			adjList[from].add(to);
			adjList[to].add(from);
		}

		// DFS 목표 방문 순서 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int order = 1; order <= V; order++) {
			int vNum = Integer.parseInt(st.nextToken());
			visitOrderTarget[vNum] = order;
		}

		// 정렬 기준 컴퍼레이터 정의
		Comparator<Integer> cmpOrderAsc = new Comparator<Integer> () {
			@Override
			public int compare(Integer v1, Integer v2) {
				return visitOrderTarget[v1] - visitOrderTarget[v2];
			}
		};
		
		// 목표 방문 순서대로 adjList 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i], cmpOrderAsc);
		}

		// dfs 수행
		Arrays.fill(visitOrder, NOT_VISITED);
		dfs(1);

		// 실제 방문 순서와 목표 방문 순서가 같은지 확인 후 결과 출력
		if (isCorrectOrder()) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}

	} // end main

	/** now로부터 dfs를 수행 */
	private static void dfs(int now) {
		if (visitOrder[now] != NOT_VISITED) {
			return;
		}
		visitOrder[now] = ++visitCnt;

		for (int next : adjList[now]) {
			dfs(next);
		}

		return;
	}

	/** visitOrder[]와 visitOrderTarget[]이 같은지 확인 후 같으면 true, 아니면 false 리턴 */
	public static boolean isCorrectOrder() {
		for (int v = 1; v <= V; v++) {
			if (visitOrderTarget[v] != visitOrder[v]) {
				return false;
			}
		}
		return true;
	}
}