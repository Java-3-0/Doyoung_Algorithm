package bj10204;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_E = 1000;

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

		final int TESTS = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine().trim(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				int from = Integer.parseInt(st.nextToken().substring(1));
				int to = Integer.parseInt(st.nextToken().substring(1));
				adjList[from].add(to);
				adjList[to].add(from); // 양방향 처리
			}

			int start = Integer.parseInt(st.nextToken().substring(1));

			// bfs 수행
			int answer = bfs(start) - 1;

			// 결과를 출력용 스트링빌더에 담기
			sb.append("The number of supervillains in 2-hop neighborhood of v");
			sb.append(start).append(" is ").append(answer).append("\n");
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

	/** bfs 수행하고 방문 정점 수를 리턴 */
	public static int bfs(int start) {
		int ret = 0;

		// 큐 선언
		Queue<Integer> q = new ArrayDeque<>();

		// 시작 정점 처리
		isVisited[start] = true;
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty() && depth <= 2) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int now = q.poll();
				ret++;

				for (int next : adjList[now]) {
					if (!isVisited[next]) {
						isVisited[next] = true;
						q.offer(next);
					}
				}
			}

			depth++;
		}
		
		return ret;
	}

}