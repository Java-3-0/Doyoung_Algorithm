// 22668KB, 168ms

package bj14928;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MOD = 20000303L;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		char[] line = br.readLine().toCharArray();
		int len = line.length;

		long answer = 0L;
		for (int i = 0; i < len; i++) {
			long digit = (long) (line[i] - '0');
			answer = (10 * answer + digit) % MOD;
		}

		System.out.println(answer);

	} // end main

}