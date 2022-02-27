// 35608KB, 568ms

// 자신을 제외한 정점에서 오는 경로 수 + 자신을 제외한 정점으로 가는 경로 수 = V - 1인 정점들을 찾는다.

package bj2458;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_V = 500;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;

	static int V, E;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 인접 행렬 입력
		boolean[][] adjMatrix = new boolean[V + 1][V + 1];
		for (int e = 0; e < E; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int shorter = Integer.parseInt(st.nextToken());
			int taller = Integer.parseInt(st.nextToken());
			adjMatrix[shorter][taller] = true;
		}
		
		// 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 구한다.
		boolean[][] existPath = floydWarshall(adjMatrix);
		
		// 자신의 키 번호를 알 수 있는 학생 수를 구한다
		int answer = 0;
		for (int student = 1; student <= V; student++) {
			int cnt = 0;
			for (int other = 1; other <= V; other++) {
				if (student == other) {
					continue;
				}
				if (existPath[student][other] || existPath[other][student]) {
					cnt++;
				}
			}
			
			if (cnt == V - 1) {
				answer++;
			}
		}
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 구해서 이차원 배열 형태로 리턴 */
	public static boolean[][] floydWarshall(boolean[][] adjMatrix) {
		boolean[][] ret = new boolean[V + 1][V + 1];

		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				ret[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 1; m <= V; m++) {
			for (int shorter = 1; shorter <= V; shorter++) {
				for (int taller = 1; taller <= V; taller++) {
					ret[shorter][taller] |= (ret[shorter][m] && ret[m][taller]);
				}
			}
		}

		return ret;
	}
}