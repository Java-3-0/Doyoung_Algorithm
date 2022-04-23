// 17412KB, 160ms

package bj5567;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500, MAX_E = 10000;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjList[from].add(to);
			adjList[to].add(from);
		}

		int answer = bfs(1);

		System.out.println(answer);

	} // end main

	/** bfs로 depth 2까지의 동기 수를 리턴 */
	public static int bfs(int start) {
		boolean[] isVisited = new boolean[V + 1];
		Queue<Integer> q = new ArrayDeque<>();

		isVisited[start] = true;
		q.offer(start);

		int depth = 0;
		int cnt = 0;
		while (!q.isEmpty() && depth <= 2) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int now = q.poll();
				cnt++;

				for (int next : adjList[now]) {
					if (!isVisited[next]) {
						isVisited[next] = true;
						q.offer(next);
					}
				}

			} // end for i

			depth++;

		} // end while

		// 본인은 제외하고 리턴
		return cnt - 1;
	}

}