// 14504KB, 116ms

package bj2156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 10000;
	public static final int CACHE_EMPTY = -1;

	public static int N;
	public static int[] wines = new int[MAX_N];

	// cache[i]는 i번 잔 이후로 가능한 최대 양
	public static int[][] cache = new int[MAX_N + 1][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();

		// 입력
		N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			wines[i] = Integer.parseInt(br.readLine());
		}

		// 가능한 최대 포도주 양 계산
		int answer = getMaxAmount(0, 0);

		// 출력
		System.out.println(answer);
	}

	/** 캐시의 모든 값을 CACHE_EMPTY로 초기화 */
	public static void initCache() {
		int length = cache.length;
		for (int i = 0; i < length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** startIdx번 잔 이후로 가능한 최대 양을 리턴 */
	public static int getMaxAmount(int startIdx, int prevCount) {
		// base case
		if (startIdx >= N) {
			return 0;
		}

		// 캐시에 계산된 값이 있는 경우
		if (cache[startIdx][prevCount] != CACHE_EMPTY) {
			return cache[startIdx][prevCount];
		}

		// 이 아래로는 새로 계산해서 캐시에 넣어야 하는 경우

		// 안 마시는 경우의 최대값
		int ret = getMaxAmount(startIdx + 1, 0);
		// 이미 연속 2잔을 마신 게 아니라면, 이번 잔을 마시는 경우의 최대값을 구해본다
		if (prevCount < 2) {
			ret = Math.max(ret, wines[startIdx] + getMaxAmount(startIdx + 1, prevCount + 1));
		}

		return cache[startIdx][prevCount] = ret;
	}

}