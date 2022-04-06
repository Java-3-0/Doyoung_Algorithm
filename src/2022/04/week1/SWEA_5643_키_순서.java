// 89952KB, 1158ms

package swea5643;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static final int MAX_V = 500;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;

	static int V, E;
	static boolean[][] adjMatrix = new boolean[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 정점 수, 간선 수 입력
			V = Integer.parseInt(br.readLine());
			E = Integer.parseInt(br.readLine());

			// 인접 행렬 입력
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

				// 다른 모두에 대해 자신이 큰지 작은지 알 수 있다면 그 학생의 키 번호를 알 수 있다
				if (cnt == V - 1) {
					answer++;
				}

			} // end for student

			// 출력 스트링빌더에 정답을 형식에 맞게 추가
			sb.append("#").append(tc).append(" ").append(answer).append("\n");

		} // end for tc
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	public static void initMemory() {
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], false);
		}
	}

	/** 플로이드 와샬 알고리즘으로 모든 정점 쌍 간의 경로 존재 여부를 구해서 이차원 배열 형태로 리턴 */
	public static boolean[][] floydWarshall(boolean[][] adjMatrix) {
		boolean[][] ret = new boolean[V + 1][V + 1];

		// adjMatrix 내용대로 ret를 초기화
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				ret[y][x] = adjMatrix[y][x];
			}
		}

		// 플로이드 와샬 알고리즘으로 ret 갱신
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