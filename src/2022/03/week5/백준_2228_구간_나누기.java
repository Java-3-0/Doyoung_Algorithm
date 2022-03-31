// 12288KB, 88ms

package bj2228;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final int MAX_M = MAX_N / 2 + 1;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, M;
	static int[] seq;
	static int[][] cache = new int[MAX_N][MAX_M];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 수열 메모리 할당
		seq = new int[MAX_N];

		// 수열 입력
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 합 최대 계산
		int answer = getMaxSum(0, M);

		// 출력
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** startIdx 위치부터 끝까지 partsToMake 개의 구간을 만드는 방법 중 합이 최대인 것을 리턴 */
	public static int getMaxSum(int startIdx, int partsToMake) {
		// base case 1. 다 만든 경우
		if (partsToMake == 0) {
			return 0;
		}

		// base case 2. 다 만들지 못한 채 끝까지 간 경우
		if (startIdx >= N) {
			return -INF;
		}

		// 캐시에 저장되어 있는 경우
		if (cache[startIdx][partsToMake] != CACHE_EMPTY) {
			return cache[startIdx][partsToMake];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		int ret = getMaxSum(startIdx + 1, partsToMake);
		for (int idx = startIdx, sum = 0; idx < N; idx++) {
			sum += seq[idx];
			ret = Math.max(ret, sum + getMaxSum(idx + 2, partsToMake - 1));
		}

		return cache[startIdx][partsToMake] = ret;
	}
}