// 11852KB, 84ms

package bj11057;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 10007;
	static final int DIGITS = 10;

	static int N;
	static int[][] cache = new int[MAX_N + 1][DIGITS];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();

		N = Integer.parseInt(br.readLine());

		int answer = solve(N);
		
		System.out.println(answer);
		
	} // end main

	public static int solve(int n) {
		int ret = 0;

		for (int i = 0; i < DIGITS; i++) {
			ret += getCounts(n - 1, i);
			ret %= MOD;
		}

		return ret;
	}

	/** 이전에 사용된 수가 prev이고 남은 길이가 n일 때, 가능한 개수를 리턴 */
	public static int getCounts(int n, int prev) {
		if (n == 0) {
			return 1;
		}

		if (cache[n][prev] != CACHE_EMPTY) {
			return cache[n][prev];
		}

		int ret = 0;
		for (int pick = prev; pick < DIGITS; pick++) {
			ret += getCounts(n - 1, pick);
			ret %= MOD;
		}

		return cache[n][prev] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}