// 53580KB, 596ms

package bj19622;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static int[] people = new int[MAX_N + 1];
	static int[][] cache = new int[MAX_N + 1][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		N = Integer.parseInt(br.readLine());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			st.nextToken();
			st.nextToken();
			people[i] = Integer.parseInt(st.nextToken());
		}

		int answer = getMaxPeople(0, 0);
		
		System.out.println(answer);
		
	} // end main

	public static int getMaxPeople(int startIdx, int usedPrev) {
		if (startIdx == N) {
			return 0;
		}
		
		if (cache[startIdx][usedPrev] != CACHE_EMPTY) {
			return cache[startIdx][usedPrev];
		}
		
		int ret = getMaxPeople(startIdx + 1, 0);
		if (usedPrev == 0) {
			ret = Math.max(ret, people[startIdx] + getMaxPeople(startIdx + 1, 1));
		}
		
		return cache[startIdx][usedPrev] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}