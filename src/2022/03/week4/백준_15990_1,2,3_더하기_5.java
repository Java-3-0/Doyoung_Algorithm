// 21908KB, 188ms

package bj15990;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000009;

	static int[][] cache = new int[MAX_N + 1][4];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		initCache();
		
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			N = Integer.parseInt(br.readLine());
			long answer = getNumberOfWays(N, 0);
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static long getNumberOfWays(int n, int prevUsed) {
		if (n < 0) {
			return 0;
		}
		if (n == 0) {
			return 1;
		}

		if (cache[n][prevUsed] != CACHE_EMPTY) {
			return cache[n][prevUsed];
		}

		int ret = 0;
		for (int num = 1; num <= 3; num++) {
			if (prevUsed != num) {
				ret += getNumberOfWays(n - num, num);
				ret %= MOD;
			}
		}

		return cache[n][prevUsed] = ret;

	}
}