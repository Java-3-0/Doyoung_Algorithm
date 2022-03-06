// 21344KB, 108ms

package bj1562;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100;
	static final int DIGITS = 10;
	static final int BITMASK = 1 << DIGITS;
	static final long MOD = 1000000000L;
	static final long CACHE_EMPTY = -1L;

	static int N;
	static long[][][] cache = new long[MAX_N][DIGITS][BITMASK];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 계단 수 개수 계산
		long answer = solve();
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** 0을 제외한 모든 시작 숫자를 완전탐색하면서, 계단 수의 개수를 구해서 리턴한다 */
	public static long solve() {
		long ret = 0L;
		for (int startNum = 1; startNum <= 9; startNum++) {
			ret = modAdd(ret, countStairNums(1, startNum, (1 << startNum)));
		}

		return ret;
	}

	/** 현재 idx와 이전에 사용된 숫자, 그리고 지금까지 사용된 모든 숫자들의 비트마스킹 정보가 주어졌을 때 가능한 계단 수의 가지수를 리턴 */
	public static long countStairNums(int nowIdx, int prevNum, int usedNums) {
		// base case: N 길이의 수가 만들어진 경우
		if (nowIdx == N) {
			// 0 ~ 9까지 모든 수를 사용했다면 성공
			if (usedNums == (BITMASK - 1)) {
				return 1;
			}
			// 아니면 실패
			else {
				return 0;
			}
		}

		// cache에 저장된 값이 있을 경우 그대로 리턴
		if (cache[nowIdx][prevNum][usedNums] != CACHE_EMPTY) {
			return cache[nowIdx][prevNum][usedNums];
		}
		
		// 새로 계산해야 하는 경우
		long ret = 0L;
		if (prevNum == 0) {
			ret = countStairNums(nowIdx + 1, prevNum + 1, (usedNums | (1 << 1)));
		}
		else if (prevNum == 9) {
			ret = countStairNums(nowIdx + 1, prevNum - 1, (usedNums | (1 << 8)));
		}
		else {
			ret = countStairNums(nowIdx + 1, prevNum - 1, (usedNums | (1 << (prevNum - 1))));
			ret = modAdd(ret, countStairNums(nowIdx + 1, prevNum + 1, (usedNums | (1 << (prevNum + 1)))));
		}
		
		return cache[nowIdx][prevNum][usedNums] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[0].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	/** 두 수의 모듈러 덧셈 결과를 리턴 */
	public static long modAdd(long a, long b) {
		return (a + b) % MOD;
	}

}