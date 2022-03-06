// 12576KB, 120ms

package bj2623;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 1000;
	static final int MAX_M = 100;

	static int N, M;
	static Set<Integer>[] adjList;
	static int[] incoming;
	static List<Integer> order = new ArrayList<>();;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력 받고, adjList, incoming 메모리 할당
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adjList = new HashSet[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new HashSet<Integer>();
		}
		incoming = new int[N + 1];

		// 출연 순서 입력받아서 adjList와 incoming 갱신
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine(), " ");
			int K = Integer.parseInt(st.nextToken());
			if (K >= 1) {
				int prev = Integer.parseInt(st.nextToken());
				for (int k = 1; k < K; k++) {
					int now = Integer.parseInt(st.nextToken());
					if (!adjList[prev].contains(now)) {
						adjList[prev].add(now);
						incoming[now]++;
					}
				
					prev = now;
				}
			}
		}
		
		// 위상 정렬 수행
		Queue<Integer> q = new LinkedList<>();
		for (int v = 1; v <= N; v++) {
			if (incoming[v] == 0) {
				q.offer(v);
			}
		}
		
		while (!q.isEmpty()) {
			int from = q.poll();
			order.add(from);
			
			for (int to : adjList[from]) {
				incoming[to]--;
				if (incoming[to] == 0) {
					q.offer(to);
				}
			}
		}
		
		// 출력
		if (order.size() == N) {
			for (int num : order) {
				sb.append(num).append("\n");
			}
			System.out.print(sb.toString());
		}
		else {
			System.out.println("0");
		}

	} // end main

}