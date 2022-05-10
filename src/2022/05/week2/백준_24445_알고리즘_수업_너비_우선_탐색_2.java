// 90780KB, 1004ms

package bj24445;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000;
	static final int MAX_E = 200000;
	static final int NOT_VISITED = 0;
	
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] visitOrder = new int[MAX_V + 1];
	static int V, E, R;
	static int visitCnt = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

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

		// 간선 정보 내림차순 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i], Collections.reverseOrder());
		}

		// 방문 여부 초기화
		Arrays.fill(visitOrder, NOT_VISITED);
		
		// bfs 수행
		bfs(R);

		// 각 정점의 방문 순서 출력
		for (int v = 1; v <= V; v++) {
			sb.append(visitOrder[v]).append("\n");
		}
		System.out.print(sb.toString());

	} // end main
	
	/** bfs 수행 */
	public static void bfs(int start) {
		// 큐 선언
		Queue<Integer> q = new ArrayDeque<>();
		
		// 시작 정점 처리
		visitOrder[start] = ++visitCnt;
		q.offer(start);
		
		while (!q.isEmpty()) {
			int now = q.poll();
			for (int next : adjList[now]) {
				if (visitOrder[next] == NOT_VISITED) {
					visitOrder[next] = ++visitCnt;
					q.offer(next);
				}
			}
		}
	}

}