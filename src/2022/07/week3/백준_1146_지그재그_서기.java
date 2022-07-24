// 12908KB, 96ms

package boj1146;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100;
	static final long CACHE_EMPTY = -1L;
	static final long MOD = 1000000L;

	static int N;
	static long[][][] cache = new long[MAX_N + 1][MAX_N + 1][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();

		N = Integer.parseInt(br.readLine());

		long answer = 0L;

		if (N == 1) {
			answer = 1L;
		} else {
			for (int first = 1; first <= N; first++) {
				for (int second = 1; second <= N; second++) {
					if (first == second) {
						continue;
					} else if (first < second) {
						answer += countNumberOfWays(second - 1 - 1, N - second, 0);
						answer %= MOD;
					} else {
						answer += countNumberOfWays(second - 1, N - second - 1, 1);
						answer %= MOD;
					}
				}
			}
		}

		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	public static long countNumberOfWays(int smallCnt, int bigCnt, int shouldFillBig) {
		int remainingCnt = smallCnt + bigCnt;

		if (remainingCnt == 0) {
			return 1L;
		}

		if (cache[smallCnt][bigCnt][shouldFillBig] != CACHE_EMPTY) {
			return cache[smallCnt][bigCnt][shouldFillBig];
		}

		long ret = 0L;

		if (shouldFillBig == 1) {
			for (int pick = 1; pick <= bigCnt; pick++) {
				ret += countNumberOfWays(smallCnt + pick - 1, bigCnt - pick, 0);
				ret %= MOD;
			}
		} else {
			for (int pick = 1; pick <= smallCnt; pick++) {
				ret += countNumberOfWays(pick - 1, bigCnt + smallCnt - pick, 1);
				ret %= MOD;
			}
		}

		return cache[smallCnt][bigCnt][shouldFillBig] = ret;
	}

}