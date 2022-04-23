// 11636KB, 76ms

package bj20500;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MOD = 1000000007;
	static final int MAX_N = 1515;
	static final int CACHE_EMPTY = -1;

	static int[][] cache = new int[MAX_N + 1][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 맨 뒷자리 5로 세팅하고, 나머지 길이에 대해 풀기
		int answer = getCount(N - 1, 5 % 3);

		// 정답 출력
		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getCount(int len, int sum) {
		if (len == 0) {
			if (sum % 5 == 0) {
				return 1;
			} else {
				return 0;
			}
		}

		if (cache[len][sum] != CACHE_EMPTY) {
			return cache[len][sum];
		}

		int ret = getCount(len - 1, (sum + 1) % 3) + getCount(len - 1, (sum + 5) % 3);
		ret %= MOD;

		return cache[len][sum] = ret;
	}
}