// 47116KB, 520ms

package bj1766;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 32000;
	static final int MAX_E = 100000;

	static int V, E;
	static List<Integer>[] adjList;
	static int[] countIncoming;

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
		countIncoming = new int[V + 1];

		// 간선 입력
		for (int e = 0; e < E; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
			countIncoming[to]++;
		}

		// 문제를 풀 순서를 구한다.
		List<Integer> answer = getOrder();

		// 출력
		for (int num : answer) {
			sb.append(num).append(" ");
		}
		sb.append("\n");
		System.out.print(sb.toString());

	} // end main

	/** 문제를 풀 순서를 구한다 */
	public static List<Integer> getOrder() {
		List<Integer> ret = new ArrayList<>(V);
		PriorityQueue<Integer> pq = new PriorityQueue<>();

		// 진입 차수가 0인 정점들을 pq에 넣는다.
		for (int v = 1; v <= V; v++) {
			if (countIncoming[v] == 0) {
				pq.offer(v);
			}
		}

		while (!pq.isEmpty()) {
			// pq에서 번호가 가장 작은 문제를 가져온다.
			int now = pq.poll();

			// 그 문제를 푼다
			ret.add(now);

			// 그 문제에서 이어진 간선들을 자르고, 진입 차수가 0이 된 정점들을 pq에 넣는다.
			for (int to : adjList[now]) {
				countIncoming[to]--;

				if (countIncoming[to] == 0) {
					pq.offer(to);
				}
			}
		}

		return ret;
	}

}