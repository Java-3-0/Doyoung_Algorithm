// 11712KB, 76ms

package bj2644;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static int V, E;
	static List<Integer>[] adjList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수 입력
		V = Integer.parseInt(br.readLine());

		// adjList 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 촌수를 계산할 두 사람 입력
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		// 간선 수 입력
		E = Integer.parseInt(br.readLine());

		// 간선 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int parent = Integer.parseInt(st.nextToken());
			int child = Integer.parseInt(st.nextToken());
			adjList[parent].add(child);
			adjList[child].add(parent);
		}

		int answer = bfs(start, end);

		System.out.println(answer);
	}

	public static int bfs(int start, int end) {
		boolean[] isVisited = new boolean[V + 1];
		Queue<Integer> q = new LinkedList<>();

		isVisited[start] = true;
		q.offer(start);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int now = q.poll();
				if (now == end) {
					return depth;
				}

				for (int next : adjList[now]) {
					if (!isVisited[next]) {
						isVisited[next] = true;
						q.offer(next);
					}
				}
			}

			depth++;
		}

		return -1;
	}
}