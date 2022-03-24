// 11528KB, 76ms

package bj16395;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 30;
	static final int MAX_K = 30;
	static final int CACHE_EMPTY = -1;

	static int[][] cache = new int[MAX_N + 1][MAX_K + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// n, k 입력
		st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		// 정답 계산
		int answer = getPascal(n, k);

		// 출력
		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getPascal(int n, int k) {
		if (k == 1 || k == n) {
			return 1;
		}

		if (cache[n][k] != CACHE_EMPTY) {
			return cache[n][k];
		}
		
		return cache[n][k] = getPascal(n - 1, k - 1) + getPascal(n - 1, k);

	}
}