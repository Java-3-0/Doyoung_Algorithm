// 116044KB, 876ms

package bj2406;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_M = 10000;
	static final int INF = 987654321;

	static int V, M;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];
	static boolean[] isSelected = new boolean[MAX_V + 1];

	static class Connection {
		int x;
		int y;

		public Connection(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

	}

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

	static class Result {
		int mst;
		List<Connection> conn;

		public Result(int mst, List<Connection> conn) {
			super();
			this.mst = mst;
			this.conn = conn;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// V, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 이미 연결된 컴퓨터들 정보 입력
		List<Connection> connections = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			connections.add(new Connection(x, y));
		}

		// 간선 정보 입력
		for (int y = 1; y <= V; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= V; x++) {
				adjMatrix[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 이미 연결된 컴퓨터 정보로 간선 정보 갱신
		for (Connection c : connections) {
			adjMatrix[c.x][c.y] = 0;
			adjMatrix[c.y][c.x] = 0;
		}

		// MST 계산
		Result res = getMST();
		
		// 출력
		sb.append(res.mst).append(" ").append(res.conn.size()).append("\n");
		for (Connection c : res.conn) {
			sb.append(c.x).append(" ").append(c.y).append("\n");
		}
		System.out.print(sb.toString());

	} // end main

	/** 2번부터 V번까지를 모두 연결하는 mst 길이를 리턴 */
	public static Result getMST() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		int mst = 0;
		List<Connection> conn = new ArrayList<>();

		// 시작 정점 처리
		int start = 2;
		pq.offer(new Edge(1, start, 0));

		// Prim 알고리즘 수행
		while (!pq.isEmpty()) {
			// 가장 가중치가 낮은 간선 poll
			Edge e = pq.poll();
			int now = e.to;
			if (isSelected[now]) {
				continue;
			}
			isSelected[now] = true;
			mst += e.weight;
			if (e.weight != 0) {
				conn.add(new Connection(e.from, e.to));
			}

			// 연결된 간선들을 pq에 넣는다
			for (int next = 2; next <= V; next++) {
				if (!isSelected[next]) {
					pq.offer(new Edge(now, next, adjMatrix[now][next]));
				}
			}
		}

		return new Result(mst, conn);
	}
}