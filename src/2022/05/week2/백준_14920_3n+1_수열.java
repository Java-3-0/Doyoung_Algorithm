// 11488KB, 80ms

package bj14920;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long idx = 1L;
		long cur = Long.parseLong(br.readLine());

		while (cur != 1L) {
			if (cur % 2L == 0L) {
				cur /= 2L;
			} else {
				cur = 3L * cur + 1L;
			}

			idx++;
		}

		System.out.println(idx);

	} // end main
}