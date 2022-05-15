// 109568KB, 1432ms

package bj25187;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000, MAX_E = 200000, MAX_Q = 100000;

	static int V, E, Q;
	static boolean[] isCleanWater = new boolean[MAX_V + 1];
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	static int[] groupNum = new int[MAX_V + 1];
	static int[] cntCleanInGroup = new int[MAX_V + 1];
	static int[] cntDirtyInGroup = new int[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수, 간선 수, 쿼리 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		// 각 정점의 청정수 여부 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int v = 1; v <= V; v++) {
			int input = Integer.parseInt(st.nextToken());
			if (input == 1) {
				isCleanWater[v] = true;
			}
		}

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjList[from].add(to);
			adjList[to].add(from);
		}

		// bfs 수행
		int group = 0;
		for (int v = 1; v <= V; v++) {
			if (!isVisited[v]) {
				bfs(v, ++group);
			}
		}

		// 쿼리 처리
		for (int q = 0; q < Q; q++) {
			int k = Integer.parseInt(br.readLine());
			int groupOfK = groupNum[k];
			int cleans = cntCleanInGroup[groupOfK];
			int dirties = cntDirtyInGroup[groupOfK];
			
			if (dirties < cleans) {
				sb.append(1);
			} else {
				sb.append(0);
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** bfs를 수행하면서 방문하는 정점들에 그룹 번호를 매기고, 그룹의 청정수와 고인물 개수를 센다 */
	public static void bfs(int start, int group) {
		Queue<Integer> q = new ArrayDeque<>();
		int cntClean = 0;
		int cntDirty = 0;

		isVisited[start] = true;
		q.offer(start);

		while (!q.isEmpty()) {
			// 큐에서 정점 하나 꺼내와서 방문
			int now = q.poll();
			groupNum[now] = group;
			if (isCleanWater[now]) {
				cntClean++;
			} else {
				cntDirty++;
			}

			// 연결된 정점들 중 방문하지 않은 것들 큐에 넣기
			for (int next : adjList[now]) {
				if (!isVisited[next]) {
					isVisited[next] = true;
					q.offer(next);
				}
			}
		}

		cntCleanInGroup[group] = cntClean;
		cntDirtyInGroup[group] = cntDirty;
	}
}