// 11492KB, 80ms

package bj13699;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 35;
	static final long CACHE_EMPTY = -1L;
	
	static long[] cache = new long[MAX_N + 1];
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();
		
		N = Integer.parseInt(br.readLine());
		
		long answer = t(N);
		
		System.out.println(answer);
		
	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache, CACHE_EMPTY);
		}
	}
	
	public static long t(int n) {
		if (n == 0) {
			return 1;
		}
		
		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}
		
		long ret = 0L;
		for (int i = 0; i <= n- 1; i++) {
			ret += t(i) * t(n - 1 - i);
		}
		
		return cache[n] = ret;
	}
}