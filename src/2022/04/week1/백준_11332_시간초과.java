// 11628KB, 76ms

package bj11332;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final long MAX_WORK = (long) 1e8;
	static final long MAX_L = 10L;
	static final long INF = MAX_WORK * MAX_L + 1L;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			String timeComplexity = st.nextToken();
			long N = Long.parseLong(st.nextToken());
			long T = Long.parseLong(st.nextToken());
			long L = Long.parseLong(st.nextToken());

			if (isTLE(timeComplexity, N, T, L)) {
				sb.append("TLE!\n");
			} else {
				sb.append("May Pass.\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isTLE(String timeComplexity, long N, long T, long L) {
		if (calc(timeComplexity, N) * T > MAX_WORK * L) {
			return true;
		}

		return false;
	}

	public static long calc(String timeComplexity, long N) {
		if (timeComplexity.equals("O(N)")) {
			return N;
		} else if (timeComplexity.equals("O(N^2)")) {
			return pow(N, 2L);
		} else if (timeComplexity.equals("O(N^3)")) {
			return pow(N, 3L);
		} else if (timeComplexity.equals("O(2^N)")) {
			return pow(2L, N);
		} else if (timeComplexity.equals("O(N!)")) {
			return factorial(N);
		}

		return 0;
	}

	public static long factorial(long a) {
		long ret = 1;
		for (int i = 1; i <= a; i++) {
			ret *= i;
			if (ret < 0) {
				return INF;
			}
		}

		return ret;

	}

	public static long pow(long a, long b) {
		if (b == 0) {
			return 1;
		}

		long half = pow(a, b / 2);
		if (half == INF) {
			return INF;
		}

		long ret = half * half;
		if (ret >= INF) {
			return INF;
		}

		if (b % 2 == 1) {
			ret *= a;
			if (ret >= INF) {
				return INF;
			}
		}

		return ret;
	}
}