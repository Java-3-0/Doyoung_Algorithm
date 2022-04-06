// 11496KB, 76ms

package bj1789;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MAX_S = 4294967295L;

	static long S;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		S = Long.parseLong(br.readLine());

		long left = 1;
		long right = MAX_S;

		while (left < right) {
			long mid = (left + right) / 2 + 1;
			if (sum(mid) > S) {
				right = mid - 1;
			} else {
				left = mid;
			}

		}

		System.out.println(left);

	} // end main

	/** 1 ~ a의 합을 리턴 */
	public static long sum(long a) {
		return a * (a + 1) / 2L;
	}
}