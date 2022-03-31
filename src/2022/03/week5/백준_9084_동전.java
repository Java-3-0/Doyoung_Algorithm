// 12976KB, 116ms

package bj9084;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 20;
	static final int MAX_MONEY = 10000;
	static final int CACHE_EMPTY = -1;

	static int N;
	static int[] coins = new int[MAX_N];
	static int[][] cache = new int[MAX_N + 1][MAX_MONEY + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 캐시 초기화
			initCache();

			// N 입력
			N = Integer.parseInt(br.readLine());

			// 각 코인의 금액 입력 (내림차순으로 하기 위해서 역순으로 넣기)
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = N - 1; i >= 0; i--) {
				coins[i] = Integer.parseInt(st.nextToken());
			}

			// 목표 금액 입력
			int M = Integer.parseInt(br.readLine());

			// 경우의 수 계산
			int answer = countWays(0, M);

			// 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	private static int countWays(int startIdx, int targetMoney) {
		// base case
		if (targetMoney == 0) {
			return 1;
		}

		if (startIdx >= N || targetMoney < 0) {
			return 0;
		}

		// 캐시에 저장되어 있는 경우
		if (cache[startIdx][targetMoney] != CACHE_EMPTY) {
			return cache[startIdx][targetMoney];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		int ret = 0;
		for (int cnt = 0; cnt <= targetMoney / coins[startIdx]; cnt++) {
			ret += countWays(startIdx + 1, targetMoney - cnt * coins[startIdx]);
		}

		return cache[startIdx][targetMoney] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}