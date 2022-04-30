// 41968KB, 968ms

package bj16202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000, MAX_E = 10000, MAX_K = 100;
	static final int INF = 987654321;

	static int V, E, K;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[][] isDeleted = new boolean[MAX_V + 1][MAX_V + 1];
	static boolean[] isSelected = new boolean[MAX_V + 1];

	static class Edge implements Comparable<Edge> {
		int from;
		int to;
		int weight;

		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}

		// V, E, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int weight = 1; weight <= E; weight++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			adjList[x].add(new Edge(x, y, weight));
			adjList[y].add(new Edge(y, x, weight));
		}

		// 턴 MST 결과 구해서 출력 스트링빌더에 추가
		for (int i = 0; i < K; i++) {
			int answer = playOneTurn();
			sb.append(answer).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** Prim 알고리즘을 통해 한 턴의 MST 게임을 시뮬레이션한 후 얻은 점수를 리턴 */
	public static int playOneTurn() {
		Arrays.fill(isSelected, false);
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		// 시작 정점 처리
		int start = 1;
		isSelected[start] = true;
		for (Edge e : adjList[start]) {
			if (!isSelected[e.to] && !isDeleted[e.from][e.to]) {
				pq.offer(e);
			}
		}

		// MST에 포함되는 간선 중 가중치가 가장 낮은 간선 정보를 저장할 변수들
		int minFrom = 0;
		int minTo = 0;
		int minWeight = INF;

		// MST에 포함하게 된 간선 개수, MST 길이
		int cnt = 0;
		int ret = 0;

		// Prim 알고리즘 수행
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge now = pq.poll();
			if (isSelected[now.to] || isDeleted[now.from][now.to]) {
				continue;
			}
			isSelected[now.to] = true;
			ret += now.weight;
			cnt++;

			// 가중치가 가장 낮은 간선 정보 갱신
			if (now.weight < minWeight) {
				minWeight = now.weight;
				minFrom = now.from;
				minTo = now.to;
			}

			// 연결된 간선들을 pq에 넣는다
			for (Edge next : adjList[now.to]) {
				if (!isSelected[next.to] && !isDeleted[next.from][next.to]) {
					pq.offer(next);
				}
			}
		}

		// MST가 완성된 경우, 가중치가 가장 작은 간선 삭제 후 리턴
		if (cnt == V - 1) {
			isDeleted[minFrom][minTo] = true;
			isDeleted[minTo][minFrom] = true;
			return ret;
		}

		// 이외의 경우 0 리턴
		else {
			return 0;
		}
	}

}