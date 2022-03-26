// 24332KB, 136ms

package bj1309;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int MOD = 9901;

	static int N;
	static int[][] cache = new int[MAX_N + 1][2];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		initCache();
		
		N = Integer.parseInt(br.readLine());
		
		int answer = countWays(0, 0);
		
		System.out.println(answer);
		
	} // end main
	
	public static int countWays(int startIdx, int usedPrev) {
		if (startIdx == N) {
			return 1;
		}
		
		if (cache[startIdx][usedPrev] != CACHE_EMPTY) {
			return cache[startIdx][usedPrev];
		}
		
		int ret = 0;
		
		// 한쪽에 배치하는 것과, 아예 배치하지 않는 것은 항상 가능
		ret += countWays(startIdx + 1, 1);
		ret %= MOD;
		ret += countWays(startIdx + 1, 0);
		ret %= MOD;
		
		// 이전 row를 쓰지 않았다면, 다른쪽에도 배치 가능
		if (usedPrev == 0) {
			ret += countWays(startIdx + 1, 1);
			ret %= MOD;
		}
		
		return cache[startIdx][usedPrev] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}