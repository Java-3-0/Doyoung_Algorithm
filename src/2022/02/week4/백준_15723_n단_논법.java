// 11564KB, 72ms

package bj15723;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int ALPHABETS = 26;

	static int E;
	static boolean[][] adjMatrix = new boolean[ALPHABETS][ALPHABETS];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 간선 개수 입력
		E = Integer.parseInt(br.readLine());

		// 인접 행렬 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = st.nextToken().charAt(0) - 'a';
			st.nextToken();
			int to = st.nextToken().charAt(0) - 'a';

			adjMatrix[from][to] = true;
		}

		// 경로 존재 여부 계산
		boolean[][] existRoute = floydWarshall();

		// 각 질문 처리
		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = st.nextToken().charAt(0) - 'a';
			st.nextToken();
			int to = st.nextToken().charAt(0) - 'a';

			if (existRoute[from][to]) {
				sb.append("T").append("\n");
			} else {
				sb.append("F").append("\n");
			}
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 2차원 배열 형태로 리턴 */
	public static boolean[][] floydWarshall() {
		// adjMatrix 내용대로 distances를 초기화
		boolean[][] existRoute = new boolean[ALPHABETS][ALPHABETS];
		for (int y = 0; y < ALPHABETS; y++) {
			for (int x = 0; x < ALPHABETS; x++) {
				existRoute[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 0; m < ALPHABETS; m++) {
			for (int from = 0; from < ALPHABETS; from++) {
				for (int to = 0; to < ALPHABETS; to++) {
					existRoute[from][to] |= (existRoute[from][m] && existRoute[m][to]);
				}
			}
		}

		return existRoute;
	}
}