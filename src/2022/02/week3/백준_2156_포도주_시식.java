// 14464KB, 108ms

package bj2156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 10000;
	public static int[] wines = new int[MAX_N];
	public static int N;
	
	// cache[i]는 i번 잔 이후로 가능한 최대 양
	public static int[][] cache = new int[MAX_N + 1][3];
	public static final int CACHE_EMPTY = -1;

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
	
	/** idx번 잔 이후로 가능한 최대 양을 리턴 */
	public static int getMaxAmount (int idx, int prevCount) {
		// base case
		if (idx >= N) {
			return 0;
		}
		
		// 캐시에 계산된 값이 있는 경우
		if (cache[idx][prevCount] != CACHE_EMPTY) {
			return cache[idx][prevCount];
		}
		
		// 이 아래로는 새로 계산해서 캐시에 넣어야 하는 경우
		
		// 안 마시는 경우의 최대값
		int exclude = getMaxAmount(idx + 1, 0);
		// 이미 연속 2잔을 마셨다면 이번 잔은 마실 수 없다.
		if (prevCount == 2) {
			return exclude;
		}
		
		// 그 외의 경우에는 마실 수도 있다.
		int include = wines[idx] + getMaxAmount(idx + 1, prevCount + 1);
		
		// 마신 경우와 안 마신 경우 중 더 나은 쪽을 리턴
		return cache[idx][prevCount] = exclude <= include ? include : exclude;
	}

}