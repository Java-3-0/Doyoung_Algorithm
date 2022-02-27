// 36676KB, 444ms

package bj1613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 400;
	static final int MAX_E = 50000;
	
	static int V, E;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		// 인접 행렬 입력
		int[][] adjMatrix = new int[V + 1][V + 1];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int before = Integer.parseInt(st.nextToken());
			int after = Integer.parseInt(st.nextToken());
			
			adjMatrix[before][after] = 1;
		}
		
		// 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 구한다.
		boolean[][] existPath = floydWarshall(adjMatrix);

		// 각 질문을 처리해서 결과를 출력 스트링빌더에 추가
		int S = Integer.parseInt(br.readLine());
		for (int s = 0; s < S; s++) {
			st = new StringTokenizer(br.readLine(), " ");
			int event1 = Integer.parseInt(st.nextToken());
			int event2 = Integer.parseInt(st.nextToken());
			
			if (existPath[event1][event2]) {
				sb.append("-1");
			}
			else if (existPath[event2][event1]) {
				sb.append("1");
			}
			else {
				sb.append("0");
			}
			sb.append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());
		
	} // end main

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 구해서 이차원 배열 형태로 리턴 */
	public static boolean[][] floydWarshall(int[][] adjMatrix) {
		boolean[][] ret = new boolean[V + 1][V + 1];
		
		// adjMatrix대로 ret 초기화
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				ret[y][x] = adjMatrix[y][x] == 1 ? true : false;
			}
		}
		
		// 플로이드 와샬 알고리즘으로 각 정점 쌍 간의 경로 존재 여부를 구한다
		for (int m = 1; m <= V; m++) {
			for (int before = 1; before <= V; before++) {
				for (int after = 1; after <= V; after++) {
					ret[before][after] |= (ret[before][m] && ret[m][after]);
				}
			}
		}
		
		return ret;
	}
}