// 12108KB, 140ms

package bj2225;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200;
	static final int MAX_K = 200;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 1000000000;

	static int N, K;
	static int[][] cache = new int[MAX_N + 1][MAX_K + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 정답 계산
		int answer = countWays(N, 0);

		// 출력
		System.out.println(answer);

	} // end main

	public static int countWays(int target, int usedCnt) {
		if (target < 0 || usedCnt > K) {
			return 0;
		}

		if (target == 0 && usedCnt == K) {
			return 1;
		}

		// 캐시에 저장되어 있는 경우
		if (cache[target][usedCnt] != CACHE_EMPTY) {
			return cache[target][usedCnt];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		int ret = 0;
		for (int pick = 0; pick <= N; pick++) {
			ret += countWays(target - pick, usedCnt + 1);
			ret %= MOD;
		}

		return cache[target][usedCnt] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}