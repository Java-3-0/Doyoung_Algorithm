// 12412KB, 88ms

package bj1495;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_M = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int FAIL = -INF;

	static int N, M, S;
	static int[] V = new int[MAX_N + 1];
	static int[][] cache = new int[MAX_N + 1][MAX_M + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		// N, S, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// V[] 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			V[i] = Integer.parseInt(st.nextToken());
		}

		int maxVolume = solve();
		
		int answer = maxVolume == FAIL ? -1 : maxVolume;
		
		System.out.println(answer);

	} // end main
	
	public static int solve() {
		int ret = Math.max(getMaxVolume(1, S - V[1]), getMaxVolume(1,  S + V[1]));
		
		return ret;
	}

	public static int getMaxVolume(int nowIdx, int nowVolume) {
		if (nowVolume < 0 || nowVolume > M) {
			return FAIL;
		}

		if (nowIdx == N) {
			return nowVolume;
		}

		if (cache[nowIdx][nowVolume] != CACHE_EMPTY) {
			return cache[nowIdx][nowVolume];
		}

		int ret = FAIL;
		int nextIdx = nowIdx + 1;
		
		ret = Math.max(ret, getMaxVolume(nextIdx, nowVolume + V[nextIdx]));
		ret = Math.max(ret, getMaxVolume(nextIdx, nowVolume - V[nextIdx]));

		return cache[nowIdx][nowVolume] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}

	}

}