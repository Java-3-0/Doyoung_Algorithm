// 17240KB, 156ms

package bj15992;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000009;

	static int[][] cache = new int[MAX_N + 1][MAX_N + 1];
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		initCache();

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			int answer = getNumberOfWays(N, M);

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** 정답 경우의 수 계산 */
	public static int getNumberOfWays(int n, int m) {
		if (n < 0 || m < 0) {
			return 0;
		}
		if (n == 0) {
			if (m == 0) {
				return 1;
			} else {
				return 0;
			}
		}

		if (cache[n][m] != CACHE_EMPTY) {
			return cache[n][m];
		}

		int ret = 0;
		ret += getNumberOfWays(n - 1, m - 1);
		ret %= MOD;
		ret += getNumberOfWays(n - 2, m - 1);
		ret %= MOD;
		ret += getNumberOfWays(n - 3, m - 1);
		ret %= MOD;

		return cache[n][m] = ret;

	}
}