// 51740KB, 188ms

package bj12865;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final int MAX_K = 100000;
	static final int MAX_W = 100000;
	static final int MAX_V = 1000;
	static final int CACHE_EMPTY = -1;

	static int N, K, W, V;
	static int[][] cache = new int[MAX_N + 1][MAX_W + 1];
	static int[] weights, values;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 캐시 초기화
		initCache();

		// 물품 수, 버틸 수 있는 무게 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 각 물건의 무게, 가치 입력
		weights = new int[N];
		values = new int[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			weights[i] = Integer.parseInt(st.nextToken());
			values[i] = Integer.parseInt(st.nextToken());
		}
		
		// 가치합의 최대 계산
		int answer = getMaxValue(0, K);
		
		// 출력
		System.out.println(answer);

	} // end main

	/** 시작 물건 번호와 남은 무게가 주어졌을 때 가능한 최대 가치를 리턴 */
	public static int getMaxValue(int startIdx, int remainingWeight) {
		// base case: 물건 끝까지 다 처리한 경우
		if (startIdx == N) {
			return 0;
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[startIdx][remainingWeight] != CACHE_EMPTY) {
			return cache[startIdx][remainingWeight];
		}
		
		// 새로 계산해서 캐시에 넣는  경우
		
		int ret = 0;
		// 1. 이 물건을 넣지 않는다.
		ret = Math.max(ret, getMaxValue(startIdx + 1, remainingWeight));
		
		// 2. 이 물건을 넣는다.
		if (weights[startIdx] <= remainingWeight) {
			ret = Math.max(ret, values[startIdx] + getMaxValue(startIdx + 1, remainingWeight - weights[startIdx]));
		}
		
		return cache[startIdx][remainingWeight] = ret;
	}
	
	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}