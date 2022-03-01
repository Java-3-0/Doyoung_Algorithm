// 11692KB, 80ms

package bj1802;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			String s = br.readLine();
			char[] arr = s.toCharArray();

			if (isPossible(arr, arr.length)) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
		}
		System.out.print(sb.toString());

	} // end main

	public static boolean isPossible(char[] arr, int len) {
		if (len < 2) {
			return true;
		}

		int midIdx = len / 2;

		for (int i = 0; i < midIdx; i++) {
			if (arr[midIdx - 1 - i] == arr[midIdx + 1 + i]) {
				return false;
			}
		}

		return isPossible(arr, midIdx - 1);
	}
}