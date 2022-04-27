// 23120KB, 244ms

package bj10610;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	static final int DIGITS = 10;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		char[] line = br.readLine().toCharArray();

		// 숫자의 개수를 센다
		Integer[] seq = new Integer[line.length];
		for (int i = 0; i < seq.length; i++) {
			seq[i] = line[i] - '0';
		}

		String answer = solve(seq);

		System.out.println(answer);

	} // end main

	public static String solve(Integer[] seq) {
		Arrays.sort(seq, Collections.reverseOrder());

		int len = seq.length;

		if (seq[len - 1] != 0) {
			return "-1";
		}

		long sum = 0L;
		for (int num : seq) {
			sum += (long) num;
		}

		StringBuilder sb = new StringBuilder();
		for (int left = 0; left < len - 1; left++) {
			if (sum % 3L == 0L) {
				for (int i = left; i < len; i++) {
					sb.append(seq[i]);
				}
				return sb.toString();
			}
		}

		return "-1";
	}
}