// 11476KB, 76ms

package bj18222;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long k = Long.parseLong(br.readLine());

		int answer = getKth(k - 1L, false);

		System.out.println(answer);

	} // end main

	public static int getKth(long k, boolean isReversed) {
		if (k == 0L) {
			if (isReversed) {
				return 1;
			} else {
				return 0;
			}
		}

		else {
			return getKth(k % (1L << (getBitLen(k) - 1L)), !isReversed);
		}
	}

	public static long getBitLen(long k) {
		long ret = 0L;

		while (k != 0L) {
			k >>= 1L;
			ret++;
		}

		return ret;
	}
}