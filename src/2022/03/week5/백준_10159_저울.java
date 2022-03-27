// 12584KB, 104ms

package bj10159;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_E = 100;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// adjMatrix 초기화
		initAdjMatrix();

		// adjMatrix 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjMatrix[from][to] = 1;
		}
		
		// 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 거리를 계산
		int[][] dists = floydWarshall();
		
		// a -> b 경로도 없고 b -> a 경로도 없으면 비교 결과를 알 수 없다.
		for (int i = 1; i <= V; i++) {
			int cnt = 0;
			for (int j = 1; j <= V; j++) {
				if (dists[i][j] == INF && dists[j][i] == INF) {
					cnt++;
				}
			}
			sb.append(cnt).append("\n");
		}
		
		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 거리를 계산해서 리턴 */
	public static int[][] floydWarshall() {
		int[][] dists = new int[V + 1][V + 1];
		
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				dists[y][x] = adjMatrix[y][x];
			}
		}
		
		for (int m = 1; m <= V; m++) {
			for (int s = 1; s <= V; s++) {
				for (int e = 1; e <= V; e++) {
					if (dists[s][m] + dists[m][e] < dists[s][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
					}
				}
			}
		}
		
		return dists;
	}
	
	/** adjMatrix를 초기화 */
	public static void initAdjMatrix() {
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (y == x) {
					adjMatrix[y][x] = 0;
				} else {
					adjMatrix[y][x] = INF;
				}
			}
		}
	}
}