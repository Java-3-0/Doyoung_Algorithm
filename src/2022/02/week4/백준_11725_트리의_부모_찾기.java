// 66880KB, 556ms

package bj11725;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());

		List<Integer> adjList[] = new ArrayList[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());

			adjList[node1].add(node2);
			adjList[node2].add(node1);
		}

		int[] parents = new int[N + 1];
		boolean[] isVisited = new boolean[N + 1];
		int root = 1;
		Queue<Integer> q = new LinkedList<>();
		isVisited[root] = true;
		q.offer(root);

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int parent = q.poll();
				for (int child : adjList[parent]) {
					if (!isVisited[child]) {
						parents[child] = parent;
						isVisited[child] = true;
						q.offer(child);
					}
				}
			}
		}
		
		for (int i = 2; i <= N; i++) {
			sb.append(parents[i]).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
}