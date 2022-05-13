// 11652KB, 80ms

package bj1351;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static Map<Long, Long> cache = new HashMap<>();

	static long N, P, Q;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Long.parseLong(st.nextToken());
		P = Long.parseLong(st.nextToken());
		Q = Long.parseLong(st.nextToken());

		long answer = getAi(N);

		System.out.println(answer);

	} // end main

	public static long getAi(long i) {
		if (i == 0L) {
			return 1L;
		}

		if (cache.containsKey(i)) {
			return cache.get(i);
		}

		long ret = getAi(i / P) + getAi(i / Q);

		cache.put(i, ret);

		return ret;
	}
}