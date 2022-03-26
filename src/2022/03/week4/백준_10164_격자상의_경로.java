// 11544KB, 84ms

package bj10164;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 15, MAX_M = 15;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, M, K;
	static int[][] cache = new int[MAX_N * MAX_M + 1][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		int answer = getNumberOfWays(1, 0);

		System.out.println(answer);

	} // end main

	public static int getNumberOfWays(int startPos, int visitedK) {
		int y = (startPos - 1) / M;
		int x = (startPos - 1) % M;

		if (!isInRange(y, x)) {
			return 0;
		}

		if (startPos == N * M) {
			if (K == 0 || visitedK == 1) {
				return 1;
			} else {
				return 0;
			}
		}

		if (cache[startPos][visitedK] != CACHE_EMPTY) {
			return cache[startPos][visitedK];
		}

		if (startPos == K) {
			visitedK = 1;
		}

		int ret = 0;
		if (x != M - 1) {
			ret += getNumberOfWays(startPos + 1, visitedK);
		}
		if (y != N - 1) {
			ret += getNumberOfWays(startPos + M, visitedK);
		}

		return cache[startPos][visitedK] = ret;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < N && 0 <= x && x < M) {
			return true;
		}
		return false;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}