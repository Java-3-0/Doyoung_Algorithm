// 66080KB, 1092ms

package bj14852;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 1000000;
	static final long CACHE_EMPTY = -1L;
	static final long MOD = 1000000007L;

	static long[] cacheFlat = new long[MAX_N + 1];
	static long[] cacheUnbalanced = new long[MAX_N + 1];
	
	
	public static void main(String[] args) throws IOException {
		// 캐시 초기화
		initCaches();
		
		// N 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		// 타일링 방법 수 계산
		long answer = getWaysFlat(N);
		
		// 출력
		System.out.println(answer);
		
	} // end main
	
	/** 캐시 초기화 */
	public static void initCaches() {
		Arrays.fill(cacheFlat, CACHE_EMPTY);
		Arrays.fill(cacheUnbalanced, CACHE_EMPTY);
	}
	
	/** 타일의 채울 차례인 위 칸과 채울 차례인 아래 칸이 같을 때, 가능한 타일링 방법의 수 */
	public static long getWaysFlat (int N) {
		// base case 1
		if (N == 0) {
			return 1L;
		}
		
		if (N < 0) {
			return 0L;
		}
		
		// 캐시에 이미 저장된 값이 있는 경우
		if (cacheFlat[N] != CACHE_EMPTY) {
			return cacheFlat[N];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		long ret = 0L;
		
		long fillOne = modMult(2L, getWaysFlat(N - 1));
		long fillTwo = getWaysFlat(N - 2);
		long fillOneAndHalf = modMult(2L, getWaysUnbalanced(N - 1));
		
		ret = modAdd(modAdd(fillOne, fillTwo), fillOneAndHalf);
		
		return cacheFlat[N] = ret;
	}
	
	/** 타일의 채울 차례인 위 칸과 채울 차례인 아래 칸이 1칸 차이가 날 때, 가능한 타일링 방법의 수 */
	public static long getWaysUnbalanced (int N) {
		// base case
		if (N == 0) {
			return 0L;
		}
		
		if (N == 1) {
			return 1L;
		}
		
		// 캐시에 이미 저장된 값이 있는 경우
		if (cacheUnbalanced[N] != CACHE_EMPTY) {
			return cacheUnbalanced[N];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		long ret = 0L;
		
		long fillOne = getWaysFlat(N - 1);
		long fillTwo = getWaysUnbalanced(N - 1);
		
		ret = modAdd(fillOne, fillTwo);
		
		return cacheUnbalanced[N] = ret;
	}
	
	/** 모듈러 덧셈 함수 */
	public static long modAdd(long a, long b) {
		return (a + b) % MOD;
	}
	
	/** 모듈러 곱셈 함수 */
	public static long modMult(long a, long b) {
		return (a * b) % MOD;
	}
}