// 12352KB, 96ms

package bj11053;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;

	static int N;
	static int[] seq;
	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		Arrays.fill(cache, CACHE_EMPTY);

		// 수열 길이 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 테스트
//		for (int i = 0; i < seq.length; i++) {
//			System.out.print(getMaxLength(i) + " ");
//		}
//		System.out.println();

		int answer = 1;

		// LIS의 최대 길이 계산
		for (int idx = 0; idx < seq.length; idx++) {
			int lenLIS = getMaxLength(idx);
			answer = answer < lenLIS ? lenLIS : answer;
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** lastIdx까지 가능한 최대 LIS의 길이를 리턴 */
	public static int getMaxLength(int lastIdx) {
		// base case
		if (lastIdx == 0) {
			return 1;
		}

		// 캐시에 저장된 값이 있는 경우
		if (cache[lastIdx] != CACHE_EMPTY) {
			return cache[lastIdx];
		}

		// 새로 계산하는 경우
		int ret = 1;

		for (int idx = 0; idx < lastIdx; idx++) {
			if (seq[idx] < seq[lastIdx]) {
				ret = Math.max(ret, 1 + getMaxLength(idx));
			}
		}

		return cache[lastIdx] = ret;
	}
}