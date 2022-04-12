package bj2417;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static long N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Long.parseLong(br.readLine());

		long left = 0L;
		long right = Long.MAX_VALUE;

		while (left < right) {
			long mid = left / 2L + right / 2L;

			if (left % 2L == 1L && right % 2L == 1L) {
				mid++;
			}

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}

		}

		System.out.println(right);

	} // end main

	public static boolean isPossible(long num) {
		if (num >= Math.pow(2, 32)) {
			return true;
		}

		long mult = num * num;

		if (mult < 0) {
			return true;
		}

		if (mult >= N) {
			return true;
		} else {
			return false;
		}

	}

}