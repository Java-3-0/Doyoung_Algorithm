// 12788KB, 148ms

package bj1574;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_R = 300, MAX_C = 300;
	static final int MAX_N = 600;
	static final int NOT_MATCHED = -1;

	static int R, C;
	static int N;
	static Map<String, Integer> nameToIdx = new HashMap<>();
	static boolean[][] adjMatrix = new boolean[MAX_R + 1][MAX_C + 1];
	static int[] isMatchedTo = new int[MAX_C + 1];
	static boolean[] isChecked = new boolean[MAX_C + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// R, C, N 입력
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		// 간선 정보 생성 (각 row마다 룩을 배치 가능한 col로의 간선)
		for (int r = 1; r <= R; r++) {
			for (int c = 1; c <= C; c++) {
				adjMatrix[r][c] = true;
			}
		}
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			adjMatrix[row][col] = false;
		}

		// 최대 매칭 수 계산
		int maxMatches = getMaxMatches();

		// 출력
		System.out.println(maxMatches);

	} // end main

	/** 최대 매칭 수를 리턴 */
	public static int getMaxMatches() {
		Arrays.fill(isMatchedTo, NOT_MATCHED);

		int ret = 0;
		for (int row = 1; row <= R; row++) {
			Arrays.fill(isChecked, false);
			if (tryMatch(row)) {
				ret++;
			}
		}

		return ret;
	}

	/** x번 인덱스의 매칭을 시도하고 성공 시 true, 실패 시 false를 리턴 */
	public static boolean tryMatch(int x) {
		for (int y = 1; y <= C; y++) {
			if (!adjMatrix[x][y]) {
				continue;
			}

			if (isChecked[y]) {
				continue;
			}
			isChecked[y] = true;

			if (isMatchedTo[y] == NOT_MATCHED || tryMatch(isMatchedTo[y])) {
				isMatchedTo[y] = x;
				return true;
			}
		}

		return false;
	}
}