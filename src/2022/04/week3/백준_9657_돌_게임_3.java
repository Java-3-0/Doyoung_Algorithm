// 11612KB, 72ms

package bj9657;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int WIN = 1, LOSE = 0;

	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();

		int N = Integer.parseInt(br.readLine());

		String answer = (canWin(N) == WIN) ? "SK" : "CY";

		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	/** n개가 남았을 때, 이번 턴 플레이어가 이길 수 있는지 여부를 리턴 */
	public static int canWin(int n) {
		// base case 1. 승리
		if (n < 0) {
			return WIN;
		}

		// base case 2. 패배
		if (n == 0) {
			return LOSE;
		}

		// 이미 캐시에 계산되어 있는 경우
		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		int result1 = canWin(n - 1);
		int result2 = canWin(n - 3);
		int result3 = canWin(n - 4);

		if (result1 == LOSE || result2 == LOSE || result3 == LOSE) {
			return cache[n] = WIN;
		} else {
			return cache[n] = LOSE;
		}

	}
}