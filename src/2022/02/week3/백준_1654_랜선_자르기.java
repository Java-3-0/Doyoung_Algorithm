// 15252KB, 128ms

package bj1654;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static final long MAX_K = 10000;
	public static final long MAX_N = 1000000;
	public static long K;
	public static long N;
	public static long[] cables;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		K = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		cables = new long[(int) K];
		for (int i = 0; i < K; i++) {
			cables[i] = Long.parseLong(br.readLine());
		}

		long lo = 0L;
		long hi = (long) (Integer.MAX_VALUE) + 1L;

		// lo <= answer < hi
		while (hi - lo > 1) {
			long mid = (lo + hi) / 2L;
			if (isPossible(mid)) {
				lo = mid;
			} else {
				hi = mid;
			}
		}

		System.out.println(lo);
	}

	/** len 길이인 것을 N개 이상 확보 가능하면 true, 아니면 false 리턴 */
	public static boolean isPossible(long len) {
		if (len == 0)
			return true;

		long count = 0L;
		for (long cable : cables) {
			count += cable / len;
		}

		if (count >= N) {
			return true;
		}

		return false;
	}
}
