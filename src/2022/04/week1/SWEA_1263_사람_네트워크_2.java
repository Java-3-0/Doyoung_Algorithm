// 95116KB, 1255ms

package swea1263;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static final int MAX_V = 1000;
	static final int INF = 987654321;

	static int V;
	static int[][] adjMatrix = new int[MAX_V][MAX_V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");

			// 정점 개수 입력
			V = Integer.parseInt(st.nextToken());

			// 인접 행렬 입력
			for (int y = 0; y < V; y++) {
				for (int x = 0; x < V; x++) {
					int input = Integer.parseInt(st.nextToken());
					adjMatrix[y][x] = input == 0 ? INF : input;
				}
			}

			// 플로이드 와샬 알고리즘으로 모든 정점 쌍 사이의 거리 계산
			int[][] dists = floydWarshall();
			
			// 각 정점마다 다른 모든 정점들로의 거리 합 계산하고 그 최소값을 갱신
			int minCC = INF;
			for (int y = 0; y < V; y++) {
				int CC = 0;
				for (int x = 0; x < V; x++) {
					if (y == x) {
						continue;
					}
					
					CC += dists[y][x];
				}
				minCC = CC < minCC ? CC : minCC;
			}

			// 출력 스트링빌더에 정답을 형식에 맞게 추가
			sb.append("#").append(tc).append(" ").append(minCC).append("\n");

		} // end for tc

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 길이 계산해서 리턴 */
	public static int[][] floydWarshall() {
		int[][] ret = new int[V][V];

		// adjMatrix의 값대로 ret 초기화
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				ret[y][x] = adjMatrix[y][x];
			}
		}

		// 플로이드 와샬 알고리즘으로 ret 갱신
		for (int m = 0; m < V; m++) {
			for (int s = 0; s < V; s++) {
				for (int e = 0; e < V; e++) {
					if (ret[s][m] + ret[m][e] < ret[s][e]) {
						ret[s][e] = ret[s][m] + ret[m][e];
					}
				}
			}
		}

		return ret;
	}
}