// 262100KB, 768ms

package boj15490;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 3000;
	static final byte WIN = 2, LOSE = 1, CACHE_EMPTY = 0;

	static int N;
	static int[] seq = new int[MAX_N];
	static byte[][][] cache = new byte[MAX_N][MAX_N][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken()) % 2;
		}

		// 게임 결과 계산
		byte result = firstPlayerWins(0, N - 1, 0, 0);

		// 출력
		if (result == WIN) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	/** 선턴의 승리 여부를 리턴 */
	public static byte firstPlayerWins(int leftIdx, int rightIdx, int mySum, int enemySum) {
		if (leftIdx > rightIdx) {
			if (mySum == 0) {
				return WIN;
			} else {
				return LOSE;
			}
		}

		if (cache[leftIdx][rightIdx][mySum] != CACHE_EMPTY) {
			return cache[leftIdx][rightIdx][mySum];
		}

		byte ret = LOSE;

		int len = rightIdx - leftIdx + 1;
		// 왼쪽에서 하나
		if (firstPlayerWins(leftIdx + 1, rightIdx, enemySum, (mySum + seq[leftIdx]) % 2) == LOSE) {
			ret = WIN;
		}

		// 오른쪽에서 하나
		if (firstPlayerWins(leftIdx, rightIdx - 1, enemySum, (mySum + seq[rightIdx]) % 2) == LOSE) {
			ret = WIN;
		}

		// 두 개 가져오는 것이 가능한 경우
		if (len >= 2) {
			// 왼쪽에서 둘
			if (firstPlayerWins(leftIdx + 2, rightIdx, enemySum, (mySum + seq[leftIdx] + seq[leftIdx + 1]) % 2) == LOSE) {
				ret = WIN;
			}
			
			// 오른쪽에서 둘
			if (firstPlayerWins(leftIdx, rightIdx - 2, enemySum, (mySum + seq[rightIdx] + seq[rightIdx - 1]) % 2) == LOSE) {
				ret = WIN;
			}
		}

		return cache[leftIdx][rightIdx][mySum] = ret;
	}

}