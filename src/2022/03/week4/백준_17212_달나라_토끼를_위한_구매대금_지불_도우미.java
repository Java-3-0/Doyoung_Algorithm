// 22392KB, 356ms

package bj17212;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100000;
	static final int INF = 987654321;
	static final int CACHE_EMPTY = -1;
	static final int[] coins = { 1, 2, 5, 7 };

	static int N;
	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();
		
		N = Integer.parseInt(br.readLine());

		int answer = solve(N);

		System.out.println(answer);

	} // end main

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}
	
	public static int solve(int n) {
		if (n == 0) {
			return 0;
		}
		if (n < 0) {
			return INF;
		}

		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}

		int ret = INF;
		for (int coin : coins) {
			ret = Math.min(ret, 1 + solve(n - coin));
		}

		return cache[n] = ret;

	}
}