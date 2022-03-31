package bj1325;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_V = 10000, MAX_E = 100000;
	static int V, E;
	static ArrayList<Integer>[] adjList;
	static int[] hackCount;
	static int[] isCycleWith;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		hackCount = new int[V + 1];
		isCycleWith = new int[V + 1];

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int to = Integer.parseInt(st.nextToken());
			int from = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
		}

		for (int v = 1; v <= V; v++) {
			Collections.sort(adjList[v]);
			
			if (isCycleWith[v] == 0) {
				hackCount[v] = bfs(v);
			} else {
				hackCount[v] = hackCount[isCycleWith[v]];
			}
		}

		int maxHack = 0;
		for (int v = 1; v <= V; v++) {
			int hack = hackCount[v];

			if (maxHack < hack) {
				maxHack = hack;
			}
		}

		for (int v = 1; v <= V; v++) {
			if (hackCount[v] == maxHack) {
				sb.append(v).append(" ");
			}
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

	/** start에서부터 bfs로 도달할 수 있는 정점들의 수를 리턴 */
	public static int bfs(int start) {
		boolean[] isVisited = new boolean[V + 1];
		int[] cameFrom = new int[V + 1];
		Queue<Integer> q = new LinkedList<>();

		isVisited[start] = true;
		q.offer(start);

		int cnt = 0;
		while (!q.isEmpty()) {
			int here = q.poll();
			cnt++;

			for (int there : adjList[here]) {
				if (!isVisited[there]) {
					isVisited[there] = true;
					cameFrom[there] = here;
					q.offer(there);

					if (there == start) {
						int tmp = there;
						while (cameFrom[tmp] != start) {
							isCycleWith[tmp] = start;
							tmp = cameFrom[tmp];
						}
					}
				}
			}
		}

		return cnt;
	}

}