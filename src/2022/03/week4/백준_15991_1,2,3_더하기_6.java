// 17444KB, 120ms

package bj15991;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000009;

	static int[] cache = new int[MAX_N + 1];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		initCache();

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			N = Integer.parseInt(br.readLine());

			int answer = 0;
			// 가운데 수를 정하고, 양쪽으로 나눠진 부분 중 한 쪽을 채우는 방법의 수를 구한다.
			for (int mid = 0; mid <= 3; mid++) {
				int remaining = N - mid;
				if (remaining >= 0 && remaining % 2 == 0) {
					answer += getNumberOfWays(remaining / 2);
					answer %= MOD;
				}
			}

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	public static long getNumberOfWays(int n) {
		if (n < 0) {
			return 0;
		}
		if (n == 0) {
			return 1;
		}

		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}

		int ret = 0;
		ret += getNumberOfWays(n - 1);
		ret %= MOD;
		ret += getNumberOfWays(n - 2);
		ret %= MOD;
		ret += getNumberOfWays(n - 3);
		ret %= MOD;
		
		return cache[n] = ret;

	}
}