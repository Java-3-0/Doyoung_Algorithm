// 25460KB, 188ms

package bj15993;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000009;

	static int[][] cache = new int[MAX_N + 1][2];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		initCache();

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());

			int answerOdd = getNumberOfWays(N, 1);
			int answerEven = getNumberOfWays(N, 0);

			sb.append(answerOdd).append(" ").append(answerEven).append("\n");
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
	public static int getNumberOfWays(int n, int shouldUse) {
		if (n < 0) {
			return 0;
		}
		if (n == 0) {
			if (shouldUse == 0) {
				return 1;
			} else {
				return 0;
			}
		}

		if (cache[n][shouldUse] != CACHE_EMPTY) {
			return cache[n][shouldUse];
		}

		int ret = 0;
		int nextShouldUse = (shouldUse + 1) % 2;
		ret += getNumberOfWays(n - 1, nextShouldUse);
		ret %= MOD;
		ret += getNumberOfWays(n - 2, nextShouldUse);
		ret %= MOD;
		ret += getNumberOfWays(n - 3, nextShouldUse);
		ret %= MOD;

		return cache[n][shouldUse] = ret;

	}
}