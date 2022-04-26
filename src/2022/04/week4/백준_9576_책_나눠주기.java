// 16876KB, 276ms

package bj9576;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000, MAX_M = 1000;

	static int N, M;
	static int[] mins;
	static int[] maxs;
	/** 확인 여부를 나타내는 배열 */
	static boolean[] isChecked;
	/** 공역의 각 원소가 정의역의 몇 번째 원소와 매칭된 상태인지 나타내는 배열 */
	static int[] isMatchedTo;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			// 전역변수 메모리 할당
			mins = new int[M + 1];
			maxs = new int[M + 1];
			isChecked = new boolean[N + 1];
			isMatchedTo = new int[N + 1];

			// 각 학생마다의 선호 책 범위 입력
			for (int x = 1; x <= M; x++) {
				st = new StringTokenizer(br.readLine(), " ");
				mins[x] = Integer.parseInt(st.nextToken());
				maxs[x] = Integer.parseInt(st.nextToken());
			}

			// 최대 매칭 수 계산
			int answer = getMaxMatches();

			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 가능한 최대 매칭 수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		for (int x = 1; x <= M; x++) {
			Arrays.fill(isChecked, false);

			if (dfs(x)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 매칭 시도 */
	public static boolean dfs(int x) {
		for (int y = mins[x]; y <= maxs[x]; y++) {
			if (!isChecked[y]) {
				isChecked[y] = true;

				// 아직 매칭되지 않았거나, 기존 매칭을 옮겨줄 수 있는 경우
				if (isMatchedTo[y] == 0 || dfs(isMatchedTo[y])) {
					isMatchedTo[y] = x;
					return true;
				}
			}
		}

		return false;
	}
}