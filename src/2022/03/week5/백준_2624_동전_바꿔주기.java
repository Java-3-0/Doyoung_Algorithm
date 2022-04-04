// 16044KB, 108ms

package bj2624;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_T = 10000;
	static final int MAX_K = 100;
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int T, K;
	static int[] values = new int[MAX_K + 1];
	static int[] counts = new int[MAX_K + 1];
	static int[][] cache = new int[MAX_K + 1][MAX_T + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// T, K 입력
		T = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		// 각 동전의 정보 입력
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			values[i] = Integer.parseInt(st.nextToken());
			counts[i] = Integer.parseInt(st.nextToken());
		}

		// 정답 계산
		int answer = getNumberOfWays(0, T);

		// 출력
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** startIdx번 동전부터 사용해서 targetMoney를 만드는 경우의 수를 리턴 */
	public static int getNumberOfWays(int startIdx, int targetMoney) {
		// base case 1. 목표 금액을 완성한 경우
		if (targetMoney == 0) {
			return 1;
		}

		// base case 2. 목표 금액을 완성하지 못하고 동전 인덱스 끝까지 간 경우
		if (startIdx == K) {
			return 0;
		}

		// 캐시에 이미 저장되어 있는 경우
		if (cache[startIdx][targetMoney] != CACHE_EMPTY) {
			return cache[startIdx][targetMoney];
		}

		// 새로 계산해서 캐시에 저장해야 하는 경우
		int val = values[startIdx];
		int cnt = counts[startIdx];

		int ret = 0;
		for (int pick = 0; pick <= cnt; pick++) {
			if (targetMoney - val * pick < 0) {
				break;
			}

			ret += getNumberOfWays(startIdx + 1, targetMoney - val * pick);
		}

		return cache[startIdx][targetMoney] = ret;
	}

}