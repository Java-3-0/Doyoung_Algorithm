// 11584KB, 76ms

package bj1213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static final int ALPHABETS = 26;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int[] counts = new int[ALPHABETS];

		char[] name = br.readLine().toCharArray();

		for (char c : name) {
			counts[c - 'A']++;
		}

		List<Character> remaining = new ArrayList<>();

		StringBuilder sbFront = new StringBuilder();
		StringBuilder sbBack = new StringBuilder();

		for (int i = 0; i < ALPHABETS; i++) {
			char c = (char) (i + 'A');

			for (int k = 0; k < counts[i] / 2; k++) {
				sbFront.append(c);
				sbBack.append(c);
			}

			if (counts[i] % 2 != 0) {
				remaining.add(c);
			}
		}

		if (remaining.size() <= 1) {
			sb.append(sbFront.toString());

			for (char c : remaining) {
				sb.append(c);
			}

			sb.append(sbBack.reverse().toString());

			System.out.println(sb.toString());
		}

		else {
			System.out.println("I'm Sorry Hansoo");
		}

	} // end main

}