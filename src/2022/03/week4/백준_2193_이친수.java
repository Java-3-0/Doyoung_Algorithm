// 11520KB, 96ms

package bj2193;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 90;
	static final int CACHE_EMPTY = -1;
	static long[][] cache = new long[MAX_N + 1][2];
	static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();
		
		N = Integer.parseInt(br.readLine());
		
		long answer = countICS(1, 1);
		
		System.out.println(answer);

	} // end main
	
	public static void initCache() {
		for (int i = 0; i <cache.length;i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** 이친수의 개수를 구해서 리턴 */
	public static long countICS (int startPos, int prevOne) {
		if (startPos == N) {
			return 1;
		}
		
		if (cache[startPos][prevOne] != CACHE_EMPTY) {
			return cache[startPos][prevOne];
		}
		
		if (prevOne == 1) {
			return cache[startPos][prevOne] = countICS(startPos + 1, 0);
		}
		else {
			return cache[startPos][prevOne] = countICS(startPos + 1, 0) + countICS(startPos + 1, 1);
		}
	}
}