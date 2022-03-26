// 16776KB, 156ms

package bj11052;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static int[] cards = new int[MAX_N + 1];
	static int[][] cache = new int[MAX_N + 1][MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			cards[i] = Integer.parseInt(st.nextToken());
		}

		int answer = getMaxCost(N, 1);

		System.out.println(answer);

	} // end main

	public static int getMaxCost(int n, int startIdx) {
		if (startIdx > N) {
			if (n == 0) {
				return 0;
			} else {
				return -INF;
			}
		}

		if (cache[n][startIdx] != CACHE_EMPTY) {
			return cache[n][startIdx];
		}

		int ret = 0;
		for (int buy = 0; buy <= n / startIdx; buy++) {
			ret = Math.max(ret, cards[startIdx] * buy + getMaxCost(n - buy * startIdx, startIdx + 1));
		}

		return cache[n][startIdx] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}