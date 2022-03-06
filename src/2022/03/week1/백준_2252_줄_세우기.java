// 45952KB, 448ms

package bj2252;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 32000;
	static final int MAX_E = 100000;

	static int V, E;
	static List<Integer>[] adjList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 리스트 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i <= V; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		int[] incoming = new int[V + 1];

		// 간선 입력
		for (int e = 0; e < E; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjList[from].add(to);
			incoming[to]++;
		}

		// 진입 차수가 0인 정점들을 큐에 넣는다.
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= V; i++) {
			if (incoming[i] == 0) {
				q.offer(i);
			}
		}

		// 큐가 빌 때까지 수행
		while (!q.isEmpty()) {
			// 큐에서 정점 하나를 빼 와서 방문한다
			int now = q.poll();
			sb.append(now).append(" ");

			// 이 정점에서 이어진 정점들의 진입 차수를 1 감소시킨다.
			for (int to : adjList[now]) {
				incoming[to]--;

				// 진입 차수가 0이 되었다면 큐에 넣는다.
				if (incoming[to] == 0) {
					q.offer(to);
				}
			}
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

}