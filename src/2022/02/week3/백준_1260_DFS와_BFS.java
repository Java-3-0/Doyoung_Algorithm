// 18416KB, 204ms

package bj1260;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static int V;
	public static int E;

	public static boolean[][] isConnected;
	public static boolean[] isVisited;

	public static StringBuilder sbDFS = new StringBuilder();
	public static StringBuilder sbBFS = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그래프 vertex 수, edge 수, 시작 노드 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());

		// 그래프 관련 메모리 할당
		isConnected = new boolean[V + 1][V + 1];
		isVisited = new boolean[V + 1];

		// 그래프 연결 관계 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int com1 = Integer.parseInt(st.nextToken());
			int com2 = Integer.parseInt(st.nextToken());

			isConnected[com1][com2] = true;
			isConnected[com2][com1] = true;
		}
		br.close();

		// dfs 수행
		dfs(start);

		// dfs를 수행하며 바뀐 isVisited를 초기화해 준 후, bfs 수행
		Arrays.fill(isVisited, false);
		bfs(start);

		// 결과 출력
		System.out.println(sbDFS.toString());
		System.out.println(sbBFS.toString());
	}

	/** dfs를 통해 탐색하면서 방문 순서대로 sbDFS에 append */
	public static void dfs(int here) {
		// 이미 방문했던 곳이면 종료
		if (isVisited[here]) {
			return;
		}

		// 방문
		isVisited[here] = true;
		sbDFS.append(here).append(" ");

		// 연결된 곳으로 재귀 호출
		for (int there = 1; there <= V; there++) {
			if (isConnected[here][there]) {
				dfs(there);
			}
		}
	}

	/** bfs를 통해 탐색하면서 방문 순서대로 sbDFS에 append */
	public static void bfs(int start) {
		Queue<Integer> queue = new LinkedList<>();

		// 시작 정점을 큐에 넣는다
		queue.offer(start);

		// 큐가 빌 때까지 수행
		while (!queue.isEmpty()) {
			// 큐에서 하나를 꺼내와서 아직 방문 안 한 곳이면 방문
			int here = queue.poll();
			if (isVisited[here])
				continue;

			isVisited[here] = true;
			sbBFS.append(here).append(" ");

			// 인접 정점들을 큐에 담음
			for (int there = 1; there <= V; there++) {
				if (isConnected[here][there]) {
					queue.offer(there);
				}
			}

		}
	}
}