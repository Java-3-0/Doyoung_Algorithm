// 13104KB, 88ms

package bj12869;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_HEALTH = 60;
	static final int INF = 987654321;
	static final int CACHE_EMPTY = -1;
	static final int[][] permu = { { 1, 3, 9 }, { 1, 9, 3 }, { 3, 1, 9 }, { 3, 9, 1 }, { 9, 1, 3 }, { 9, 3, 1 } };

	static int[] seq;
	static int[][][] cache = new int[MAX_HEALTH + 1][MAX_HEALTH + 1][MAX_HEALTH + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		int N = Integer.parseInt(br.readLine());

		seq = new int[3];
		Arrays.fill(seq, 0);
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		int answer = getMinAttacks(seq[0], seq[1], seq[2]);
		
		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

	public static int getMinAttacks(int first, int second, int third) {
		if (first == 0 && second == 0 && third == 0) {
			return 0;
		}

		if (cache[first][second][third] != CACHE_EMPTY) {
			return cache[first][second][third];
		}

		int nextFirst, nextSecond, nextThird;

		int ret = INF;
		for (int[] attack : permu) {
			nextFirst = Math.max(0, first - attack[0]);
			nextSecond = Math.max(0, second - attack[1]);
			nextThird = Math.max(0, third - attack[2]);
			ret = Math.min(ret, 1 + getMinAttacks(nextFirst, nextSecond, nextThird));
		}

		return cache[first][second][third] = ret;
	}

}