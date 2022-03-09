package bj16991;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static class Position {
		double x;
		double y;

		public Position(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public double getDistanceTo(Position p) {
			double dx = p.x - this.x;
			double dy = p.y - this.y;
			return Math.sqrt(dx * dx + dy * dy);
		}
	}

	static final int MAX_V = 16;
	static final int BITMASK = (1 << MAX_V);
	static final double CACHE_EMPTY = -1.0;

	static final double INF = 9876543210987654321.0;

	static int V;
	static Position[] cities = new Position[MAX_V];
	static double[][] adjMatrix = new double[MAX_V][MAX_V];
	static double[][] cache = new double[MAX_V][BITMASK];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 도시 수 입력
		V = Integer.parseInt(br.readLine());

		// 도시 위치 입력
		for (int i = 0; i < V; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			cities[i] = new Position(x, y);
		}
		
		// 인접 행렬 계산
		for (int f = 0; f < V; f++) {
			for (int t = 0; t < V; t++) {
				Position from = cities[f];
				Position to = cities[t];
				double dist = from.getDistanceTo(to);
				adjMatrix[f][t] = dist;
			}
		}

		// 외판원 순회 최소 비용을 구한다.
		double answer = minCost(0, (1 << 0));

		// 출력
		System.out.println(answer);

	} // end main

	/** 현재 위치와 현재까지 방문한 정점 비트마스킹 정보가 주어졌을 때, 남은 경로의 최소 코스트를 리턴 */
	public static double minCost(int nowIdx, int visited) {
		// base case : 모두 방문한 경우
		if (visited == (1 << V) - 1) {
			// 시작점까지 돌아가는 거리를 리턴
			return adjMatrix[nowIdx][0];
		}

		// 캐시에 이미 계산되어 있는 경우
		if (cache[nowIdx][visited] != CACHE_EMPTY) {
			return cache[nowIdx][visited];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		// 아직 방문하지 않은 정점들로 가 보면서 ret 값 갱신
		double ret = INF;
		for (int to = 0; to < V; to++) {
			if (adjMatrix[nowIdx][to] != INF && (visited & (1 << to)) == 0) {
				ret = Math.min(ret, adjMatrix[nowIdx][to] + minCost(to, (visited | (1 << to))));
			}
		}

		return cache[nowIdx][visited] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}