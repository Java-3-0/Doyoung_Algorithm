// 151968KB, 832ms

package bj10282;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 50000;
	static final int MAX_E = 50000;
	static final long INF = 987654321098765L;

	static int V;
	static int E;
	static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

	/** 간선 객체 */
	static class Edge {
		int to;
		long weight;

		public Edge(int to, long weight) {
			super();
			this.to = to;
			this.weight = weight;
		}

	}

	/** 다익스트라 알고리즘 우선순위큐에 넣기 위한 정점 객체 */
	static class Vertex implements Comparable<Vertex> {
		int vNum;
		long distance;

		public Vertex(int vNum, long distance) {
			super();
			this.vNum = vNum;
			this.distance = distance;
		}

		@Override
		public int compareTo(Vertex v) {
			return Long.compare(this.distance, v.distance);
		}

	}

	static class Result {
		int cnt;
		long maxDist;

		public Result(int cnt, long maxDist) {
			super();
			this.cnt = cnt;
			this.maxDist = maxDist;
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

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 정점 수, 간선 수, 시작 정점 번호 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				long weight = Long.parseLong(st.nextToken());

				adjList[b].add(new Edge(a, weight));
			}

			long[] distances = dijkstra(start);

			Result answer = getAnswer(distances);

			sb.append(answer.cnt).append(" ").append(answer.maxDist).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
	}

	public static Result getAnswer(long[] distances) {
		int cnt = 0;
		long maxDist = 0L;

		for (int i = 1; i <= V; i++) {
			long dist = distances[i];
			if (dist != INF) {
				cnt++;
				if (maxDist < dist) {
					maxDist = dist;
				}
			}
		}

		return new Result(cnt, maxDist);
	}

	/** 다익스트라 알고리즘으로 start에서부터 다른 정점들로의 최소 비용을 리턴 */
	public static long[] dijkstra(int start) {
		boolean[] isVisited = new boolean[V + 1];
		long[] ret = new long[V + 1];
		PriorityQueue<Vertex> pq = new PriorityQueue<>();

		// ret 초기화
		Arrays.fill(ret, INF);

		// 시작 정점 처리
		pq.offer(new Vertex(start, 0));

		// 다익스트라 알고리즘 수행
		while (!pq.isEmpty()) {
			Vertex now = pq.poll();

			if (isVisited[now.vNum]) {
				continue;
			}

			isVisited[now.vNum] = true;
			ret[now.vNum] = now.distance;

			for (Edge e : adjList[now.vNum]) {
				if (!isVisited[e.to]) {
					pq.offer(new Vertex(e.to, now.distance + e.weight));
				}
			}
		}

		return ret;
	}
}