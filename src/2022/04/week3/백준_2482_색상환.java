// 39948KB, 212ms

package bj2482;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	static final int MAX_N = 1000;
	static final int MAX_K = MAX_N;
	static final int CACHE_EMPTY = -1;
	static final int MOD = (int) (1e9 + 3);

	static int N, K;

	static int[][][] cache = new int[MAX_N + 1][MAX_K + 1][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		initCache();

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		int answer = getNumberOfWays(2, 1, 1) + getNumberOfWays(1, 0, 0);
		answer %= MOD;

		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	public static int getNumberOfWays(int startIdx, int cnt, int usedFirst) {
		// base case 1: 모두 채운 경우
		if (startIdx >= N) {
			if (cnt == K) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		// base case 2: 마지막 칸 처리
		if (startIdx == N - 1) {
			if (usedFirst == 1) {
				if (cnt == K) {
					return 1;
				} else {
					return 0;
				}
			} else {
				if (cnt == K || cnt == K - 1) {
					return 1;
				} else {
					return 0;
				}
			}
		}

		// 이미 캐시에 저장되어 있는 경우
		if (cache[startIdx][cnt][usedFirst] != CACHE_EMPTY) {
			return cache[startIdx][cnt][usedFirst];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		int ret = getNumberOfWays(startIdx + 2, cnt + 1, usedFirst);
		ret += getNumberOfWays(startIdx + 1, cnt, usedFirst);
		ret %= MOD;

		return cache[startIdx][cnt][usedFirst] = ret;

	}

}