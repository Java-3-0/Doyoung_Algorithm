// 11488KB, 72ms

package bj2302;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	static final int MAX_N = 40;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, M;
	static List<Integer> parts = new ArrayList<>();
	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();

		// N, M 입력
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		// 고정석으로 끊어서 나머지 부분의 길이들을 리스트에 저장
		int lastFixed = 0;
		for (int i = 0; i < M; i++) {
			int fixed = Integer.parseInt(br.readLine());
			parts.add(fixed - lastFixed - 1);
			lastFixed = fixed;
		}
		parts.add(N - lastFixed);
		
		// 정답 계산 후 출력
		int answer = 1;
		for (int num : parts) {
			answer *= countWays(num);
		}
		
		System.out.println(answer);
		
		
	} // end main

	/** 고정석이 없는 연속된 n개 칸에서 배치 방법 수를 리턴 */
	public static int countWays(int n) {
		if (n <= 1) {
			return 1;
		}
		
		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}
		
		return cache[n] = countWays(n - 1) + countWays(n - 2);
	}

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

}