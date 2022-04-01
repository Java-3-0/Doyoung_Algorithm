// 11752KB, 76ms

package bj1535;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 20;
	static final int MAX_HEALTH = 100;
	static final int CACHE_EMPTY = -1;

	static int N;
	static int[][] cache = new int[MAX_N + 1][MAX_HEALTH + 1];
	static int[] loss = new int[MAX_N + 1];
	static int[] happy = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 사람 수 입력
		N = Integer.parseInt(br.readLine());

		// 잃는 체력 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			loss[i] = Integer.parseInt(st.nextToken());
		}

		// 얻는 기쁨 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			happy[i] = Integer.parseInt(st.nextToken());
		}

		// 최대 기쁨 계산
		int answer = getMaxHappiness(0, MAX_HEALTH);

		// 출력
		System.out.println(answer);
		
	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** startIdx번 사람부터 curHealth가 남아있을 때 얻을 수 있는 최대 기쁨을 리턴 */
	public static int getMaxHappiness(int startIdx, int curHealth) {
		if (startIdx == N) {
			return 0;
		}
		
		if (cache[startIdx][curHealth] != CACHE_EMPTY) {
			return cache[startIdx][curHealth];
		}
		
		int ret = getMaxHappiness(startIdx + 1, curHealth);
		
		if (curHealth - loss[startIdx] > 0) {
			ret = Math.max(ret, happy[startIdx] + getMaxHappiness(startIdx + 1, curHealth - loss[startIdx]));
		}

		return cache[startIdx][curHealth] = ret;
	}

}