// 11524KB, 80ms

package bj1562;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 100;
	public static final int DIGITS = 10;
	public static final int EMPTY = -1;
	public static final long MOD = 1000000000;
	public static long[][] cache = new long[MAX_N + 1][DIGITS + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 캐시 초기화
		initCache();
		
		// 입력
		int N = Integer.parseInt(br.readLine());
		
		// 정답 계산해서 출력
		System.out.println(solve(N));
	}
	
	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], EMPTY);
		}
	}
	
	/** 길이가 N인 계단 수의 개수를 리턴 */
	public static long solve (int N)
	{
		long ret = 0;
		for (int firstNum = 1; firstNum <= 9; firstNum++) {
			ret += countStairNumbers(N - 1, firstNum);
			ret %= MOD;
		}
		
		return ret;
	}
	
	/** 이전에 놓인 수가 prev일 때, 길이가 n인 계단 수의 개수를 리턴 */
	public static long countStairNumbers (int n, int prev) {
		// base case
		if (n == 0) return 1;
		
		// 캐시에 계산되어 있는 경우
		if (cache[n][prev] != EMPTY) {
			return cache[n][prev];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		
		// 이전 수가 9이면, 이번에 놓는 수는 8만 가능
		if (prev == 9) {
			return cache[n][prev] = countStairNumbers(n - 1, prev - 1);
		}
		
		// 이전 수가 0이면, 이번에 놓는 수는 1만 가능
		if (prev == 0) {
			return cache[n][prev] = countStairNumbers(n - 1, prev + 1);
		}
		
		// 이외의 경우, 이번에 놓는 수는 prev + 1, prev - 1 모두 가능
		return cache[n][prev] = (countStairNumbers(n - 1, prev + 1) + countStairNumbers(n - 1, prev - 1)) % MOD;
	}

}