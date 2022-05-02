// 11832KB, 92ms

package bj1414;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
	static final int MAX_V = 50;
	static final int INF = 987654321;
	static final int ALPHABETS = 26;
	static final int FAIL = -1;

	static int V;
	static int[][] lans = new int[MAX_V + 1][MAX_V + 1];
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];
	static boolean[] isSelected = new boolean[MAX_V + 1];

	static class Edge implements Comparable<Edge> {
		int to;
		int weight;

		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}

		@Override
		public String toString() {
			return "Edge [to=" + to + ", weight=" + weight + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// V 입력
		V = Integer.parseInt(br.readLine());

		// 랜선 길이 정보 입력
		for (int y = 1; y <= V; y++) {
			String line = br.readLine();
			for (int x = 1; x <= V; x++) {
				char c = line.charAt(x - 1);
				int weight = charToNum(c);
				lans[y][x] = weight;
			}
		}

		// 랜선 길이 정보에서 간선 정보 얻기
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				adjMatrix[y][x] = Math.min(lans[y][x], lans[x][y]);
			}
		}

		// MST 길이 계산
		int mst = getMST();

		// 기부 가능 랜선 길이 출력
		if (mst == FAIL) {
			System.out.println(FAIL);
		} else {
			int sum = 0;
			for (int y = 1; y <= V; y++) {
				for (int x = 1; x <= V; x++) {
					int weight = lans[y][x];
					if (weight != INF) {
						sum += weight;
					}
				}
			}

			int answer = sum - mst;
			System.out.println(answer);
		}
	} // end main

	/** mst 길이를 리턴 */
	public static int getMST() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		// 시작 정점 처리
		int start = 1;
		pq.offer(new Edge(start, 0));

		// Prim 알고리즘 수행
		int ret = 0;
		int cnt = 0;
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge e = pq.poll();
			int now = e.to;
			if (isSelected[now]) {
				continue;
			}

			isSelected[now] = true;
			ret += e.weight;
			cnt++;

			// 연결된 간선들을 pq에 넣는다
			for (int next = 1; next <= V; next++) {
				if (!isSelected[next] && adjMatrix[now][next] != INF) {
					pq.offer(new Edge(next, adjMatrix[now][next]));
				}
			}
		}

		if (cnt == V) {
			return ret;
		} else {
			return FAIL;
		}
	}

	public static int charToNum(char c) {
		if ('a' <= c && c <= 'z') {
			return c - 'a' + 1;
		} else if ('A' <= c && c <= 'Z') {
			return c - 'A' + 1 + ALPHABETS;
		} else if (c == '0') {
			return INF;
		} else {
			return FAIL;
		}
	}

}