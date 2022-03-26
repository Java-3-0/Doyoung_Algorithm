// 11960KB, 80ms

package bj2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_LEN = 5000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int MOD = 1000000;

	static int LEN;
	static int[] password = new int[MAX_LEN + 1];
	static int[] cache = new int[MAX_LEN + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		initCache();
		
		String input = br.readLine();
		LEN = input.length();
		
		for (int i = 0; i < LEN; i++) {
			password[i] = input.charAt(i) - '0';
		}
		
		int answer = getCounts(0);
		
		System.out.println(answer);
		
	} // end main
	
	public static int getCounts(int startIdx) {
		// base case
		if (startIdx > LEN - 1) {
			return 1;
		}
		
		int first = password[startIdx];
		
		if (first == 0) {
			return 0;
		}
		
		if (startIdx == LEN - 1) {
			return 1;
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[startIdx] != CACHE_EMPTY) {
			return cache[startIdx];
		}
		
		// 새로 계산하는 경우
		int second = password[startIdx + 1];
		int num = first * 10 + second;
		
		int ret = 0;
		// 1자리로 처리하는 경우
		ret += getCounts(startIdx + 1);
		ret %= MOD;
		
		// 2자리로 처리하는 경우
		if (1 <= num && num <= 26) {
			ret += getCounts(startIdx + 2);
			ret %= MOD;
		}
		
		return cache[startIdx] = ret;
		
	}

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

}