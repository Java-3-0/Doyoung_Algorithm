// 11528KB, 84ms

package bj17175;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 50;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000007;

	static int N;
	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 정답 계산
		int answer = solve(N);

		// 출력
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	public static int solve(int n) {
		if (n < 2) {
			return 1;
		}

		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}

		int ret = 1 + solve(n - 1) + solve(n - 2);
		ret %= MOD;

		return cache[n] = ret;
	}

}