// 11520KB, 128ms

package bj14445;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Long N = Long.parseLong(br.readLine());

		long answer = 0L;

		if (N == 1L) {
			answer = 0L;
		} else {
			if (N % 2L == 0L) {
				answer = N / 2L;
			} else {
				answer = N / 2L + 1;
			}
		}

		System.out.println(answer);

	} // end main

}