// 11984KB, 100ms

package bj12026;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	static final int MAX_N = 1000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static int[] seq = new int[MAX_N + 1];
	static int[][] cache = new int[MAX_N + 1][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();
		
		N = Integer.parseInt(br.readLine());

		char[] line = br.readLine().toCharArray();
		for (int i = 0; i < N; i++) {
			char c = line[i];
			switch (c) {
			case 'B':
				seq[i] = 0;
				break;
			case 'O':
				seq[i] = 1;
				break;
			case 'J':
				seq[i] = 2;
				break;
			}
		}

		int minEnergy = getMinEnergy(0, 2);
		
		int answer = INF <= minEnergy ? -1 : minEnergy;
		System.out.println(answer);

	} // end main
	
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getMinEnergy(int startIdx, int prev) {
		if ((prev + 1) % 3 != seq[startIdx]) {
			return INF;
		}
		
		if (startIdx == N - 1) {
			return 0;
		}
		
		if (cache[startIdx][prev] != CACHE_EMPTY) {
			return cache[startIdx][prev];
		}
		
		int ret = INF;
		
		for (int nextIdx = startIdx + 1; nextIdx <= N - 1; nextIdx++) {
			int jumpCost = (nextIdx - startIdx) * (nextIdx - startIdx);
			ret = Math.min(ret, jumpCost + getMinEnergy(nextIdx, seq[startIdx]));
		}
		
		return cache[startIdx][prev] = ret;
	}
	
}