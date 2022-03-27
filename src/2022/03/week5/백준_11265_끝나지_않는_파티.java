// 27072KB, 372ms

package bj11265;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500, MAX_M = 10000, MAX_C = 1000000000;
	static final int INF = MAX_C + 1;
	static final String SUCCESS = "Enjoy other party";
	static final String FAIL = "Stay here";

	static int N, M;
	static int[][] adjMatrix = new int[MAX_N + 1][MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// adjMatrix 입력
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				adjMatrix[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 계산
		int[][] dists = floydWarshall();

		// M개의 쿼리 요청 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int timeLimit = Integer.parseInt(st.nextToken());

			// timeLimit보다 최단 경로가 짧으면 성공
			if (dists[from][to] <= timeLimit) {
				sb.append(SUCCESS).append("\n");
			} else {
				sb.append(FAIL).append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 계산 */
	public static int[][] floydWarshall() {
		int[][] dists = new int[N + 1][N + 1];

		for (int y = 1; y <= N; y++) {
			for (int x = 1; x <= N; x++) {
				dists[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 1; m <= N; m++) {
			for (int s = 1; s <= N; s++) {
				for (int e = 1; e <= N; e++) {
					if (dists[s][m] + dists[m][e] < dists[s][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
					}
				}
			}
		}

		return dists;

	}

}