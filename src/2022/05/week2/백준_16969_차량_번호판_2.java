// 22472KB, 168ms

package bj16969;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long DIGITS = 10L;
	static final long ALPHABETS = 26L;

	static final long MOD = 1000000009L;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		char text[] = br.readLine().toCharArray();

		long answer = text[0] == 'c' ? ALPHABETS : DIGITS;

		char prev = text[0];
		for (int i = 1; i < text.length; i++) {
			char now = text[i];

			long possibles = now == 'c' ? ALPHABETS : DIGITS;
			if (prev == now) {
				possibles--;
			}

			answer *= possibles;
			answer %= MOD;

			prev = now;
		}

		System.out.println(answer);

	} // end main
}