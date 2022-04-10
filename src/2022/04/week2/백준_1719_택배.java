// 19364KB, 280ms

package bj1719;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200;
	static final int MAX_E = 10000;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 행렬 초기화
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
			adjMatrix[i][i] = 0;
		}

		// 인접 행렬 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjMatrix[from][to] = weight;
			adjMatrix[to][from] = weight;
		}

		// 모든 정점 쌍 간의 거리 초기화
		int[][] dists = new int[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				dists[y][x] = adjMatrix[y][x];
			}
		}

		// 모든 정점 쌍 간의 경로에서 첫 이동 정점 초기화
		int[][] firstMoves = new int[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				firstMoves[y][x] = x;
			}
		}

		// 플로이드 와샬 알고리즘 수행
		for (int m = 1; m <= V; m++) {
			for (int s = 1; s <= V; s++) {
				for (int e = 1; e <= V; e++) {
					if (dists[s][e] > dists[s][m] + dists[m][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
						firstMoves[s][e] = firstMoves[s][m];
					}
				}
			}
		}

		// 정답 출력
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (y == x) {
					sb.append("-").append(" ");
				} else {
					sb.append(firstMoves[y][x]).append(" ");
				}
			}

			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}