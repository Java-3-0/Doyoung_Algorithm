// 13520KB, 144ms

package bj11403;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int INF = 987654321;

	static int V, E;
	static boolean[][] adjMatrix = new boolean[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 개수, 간선 개수 입력
		V = Integer.parseInt(br.readLine());

		// 인접 행렬 입력
		for (int y = 1; y <= V; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= V; x++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == 1) {
					adjMatrix[y][x] = true;
				}
			}
		}
		
		boolean[][] existRoute = floydWarshall();
		
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (existRoute[y][x]) {
					sb.append("1").append(" ");
				}
				else {
					sb.append("0").append(" ");
				}
			}
			sb.append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 2차원 배열 형태로 리턴 */
	public static boolean[][] floydWarshall() {
		// adjMatrix 내용대로 distances를 초기화
		boolean[][] existRoute = new boolean[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				existRoute[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 1; m <= V; m++) {
			for (int from = 1; from <= V; from++) {
				for (int to = 1; to <= V; to++) {
					existRoute[from][to] |= (existRoute[from][m] && existRoute[m][to]);
				}
			}
		}

		return existRoute;
	}
}