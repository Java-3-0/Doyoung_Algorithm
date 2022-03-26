// 11536KB, 84ms

package bj20152;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_SIZE = 30;
	static final long CACHE_EMPTY = -1L;

	static int H, N;
	static long[][] cache = new long[MAX_SIZE + 1][MAX_SIZE + 1];
	static int delta;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();
		
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		if (N >= H) {
			delta = 1;
		} else {
			delta = -1;
		}
		
		long answer = solve(H, H);

		System.out.println(answer);

	} // end main
	
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static long solve(int y, int x) {
		if (y == N && x == N) {
			return 1L;
		}
		
		if (cache[y][x] != CACHE_EMPTY) {
			return cache[y][x];
		}
		
		long ret = 0L;
		if (isValid(y + delta, x)) {
			ret += solve(y + delta, x);
		}
		if (isValid(y, x + delta)) {
			ret += solve(y, x + delta);
		}
		
		return cache[y][x] = ret;
	}

	public static boolean isValid(int y, int x) {
		if (y > x) {
			return false;
		}
		
		if (delta >= 0) {
			if (H <= y && y <= N && H <= x && x <= N) {
				return true;
			}
			return false;
		}
		else {
			if (N <= y && y <= H && N <= x && x <= H) {
				return true;
			}
			return false;
		}
	}
}