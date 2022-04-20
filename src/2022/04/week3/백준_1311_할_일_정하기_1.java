// 94016KB, 384ms

package bj1311;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 20;
	static final int INF = 987654321;
	static final int CACHE_EMPTY = -1;

	static int N;
	static int[][] costs;
	static int[][] cache;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		costs = new int[N][N];
		cache = new int[N][(1 << N)];

		initCache();

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				costs[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int answer = getMinCost(0, 0);

		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getMinCost(int personIdx, int jobsDone) {
		if (personIdx == N) {
			return 0;
		}

		if (cache[personIdx][jobsDone] != CACHE_EMPTY) {
			return cache[personIdx][jobsDone];
		}

		int ret = INF;

		for (int job = 0; job < N; job++) {
			if ((jobsDone & (1 << job)) != 0) {
				continue;
			}

			ret = Math.min(ret, costs[personIdx][job] + getMinCost(personIdx + 1, (jobsDone | (1 << job))));
		}

		return cache[personIdx][jobsDone] = ret;
	}

}