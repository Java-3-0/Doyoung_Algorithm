// 11768KB, 88ms

package bj14938;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_RANGE = 15;
	static final int MAX_E = 100;
	static final int INF = 987654321;

	static int V, range, E;
	static int[] items = new int[MAX_V + 1];
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 개수, 수색 범위, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		range = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 각 구역 아이템 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= V; i++) {
			items[i] = Integer.parseInt(st.nextToken());
		}

		// adjMatrix 초기화
		for (int y = 0; y < adjMatrix.length; y++) {
			Arrays.fill(adjMatrix[y], INF);
			adjMatrix[y][y] = 0;
		}

		// 양방향 간선 E개 입력
		for (int i = 1; i <= E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int place1 = Integer.parseInt(st.nextToken());
			int place2 = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			if (weight < adjMatrix[place1][place2]) {
				adjMatrix[place1][place2] = weight;
			}
			if (weight < adjMatrix[place2][place1]) {
				adjMatrix[place2][place1] = weight;
			}

		}

		// 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 거리 계산
		int[][] distances = floydWarshall();

		// 각 시작 위치부터 아이템 파밍 개수를 세고, 그 중 최대값을 계산
		int maxAcquire = 0;
		for (int startPos = 1; startPos <= V; startPos++) {
			int acquire = 0;
			for (int to = 1; to <= V; to++) {
				if (distances[startPos][to] <= range) {
					acquire += items[to];
				}
			}
			if (maxAcquire < acquire) {
				maxAcquire = acquire;
			}
		}

		// 출력
		System.out.println(maxAcquire);

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 사이의 거리를 계산해서 이차원 배열 형태로 리턴 */
	public static int[][] floydWarshall() {
		// dists 초기화
		int[][] dists = new int[V + 1][V + 1];
		for (int from = 1; from <= V; from++) {
			for (int to = 1; to <= V; to++) {
				dists[from][to] = adjMatrix[from][to];
			}
		}

		// 플로이드 와샬 알고리즘 수행
		for (int m = 1; m <= V; m++) {
			for (int s = 1; s <= V; s++) {
				for (int e = 1; e <= V; e++) {
					if (dists[s][e] > dists[s][m] + dists[m][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
					}
				}
			}
		}

		return dists;
	}
}