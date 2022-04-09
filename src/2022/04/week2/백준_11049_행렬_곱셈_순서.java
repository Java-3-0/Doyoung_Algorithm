// 15012KB, 256ms

package bj11049;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500;
	static final long INF = 987654321987654321L;
	static final long CACHE_EMPTY = -1L;

	static int N;
	static long[] rowSizes = new long[MAX_N];
	static long[] colSizes = new long[MAX_N];
	static long[][] cache = new long[MAX_N + 1][MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		initCache();

		N = Integer.parseInt(br.readLine());

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			rowSizes[i] = Long.parseLong(st.nextToken());
			colSizes[i] = Long.parseLong(st.nextToken());
		}

		long answer = getMinOperations(0, N - 1);

		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static long getMinOperations(int start, int end) {
		if (start - end == 0) {
			return 0L;
		}

		if (cache[start][end] != CACHE_EMPTY) {
			return cache[start][end];
		}

		long ret = INF;

		for (int i = start; i <= end - 1; i++) {
			long operations = getMinOperations(start, i) + getMinOperations(i + 1, end);
			operations += rowSizes[start] * colSizes[i] * colSizes[end];
			ret = operations < ret ? operations : ret;
		}

		return cache[start][end] = ret;

	}

}