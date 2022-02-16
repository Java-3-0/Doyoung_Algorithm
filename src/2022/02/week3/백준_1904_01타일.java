// 63944KB, 260ms

package bj1904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 1000000;
	public static int[] cache = new int[MAX_N + 1];
	public static final int EMPTY = -1;
	public static final int MOD = 15746;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();
		
		int N = Integer.parseInt(br.readLine());
		
		System.out.println(solve(N));
	}

	public static void initCache() {
		Arrays.fill(cache, EMPTY);
	}

	public static int solve(int N) {
		if (N == 1)
			return 1;
		if (N == 2)
			return 2;

		if (cache[N] != EMPTY) {
			return cache[N];
		}

		return cache[N] = (solve(N - 1) + solve(N - 2)) % MOD;
	}

}