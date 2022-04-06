// 12936KB, 84ms

package bj1563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 1000;
	static final int MOD = 1000000;
	static final int CACHE_EMPTY = -1;

	static int[][][] cache = new int[MAX_N + 1][2][3];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();
		
		// N 입력
		N = Integer.parseInt(br.readLine());

		// 정답 계산
		int answer = getNumberOfWays(N, 0, 0);

		// 정답 출력
		System.out.println(answer);
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	/** 모든 순열을 완전탐색하면서 개근상을 받는 경우의 수를 answer에 센다 */
	public static int getNumberOfWays(int days, int totalL, int consecutiveA) {
		// base case 1. 개근상 실패
		if (totalL >= 2 || consecutiveA >= 3) {
			return 0;
		}
		
		// base case 2. 개근상 성공
		if (days == 0) {
			return 1;
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[days][totalL][consecutiveA] != CACHE_EMPTY) {
			return cache[days][totalL][consecutiveA];
		}

		// 새로 계산하는 경우
		int ret = 0;
		ret += getNumberOfWays(days - 1, totalL, 0);
		ret %= MOD;
		ret += getNumberOfWays(days - 1, totalL + 1, 0);
		ret %= MOD;
		ret += getNumberOfWays(days - 1, totalL, consecutiveA + 1);
		ret %= MOD;

		return cache[days][totalL][consecutiveA] = ret;
	}
}