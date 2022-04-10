// 11476KB, 72ms

package bj4659;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		while (true) {
			String line = br.readLine();
			if (line.equals("end")) {
				break;
			}

			if (isAcceptable(line)) {
				sb.append("<").append(line).append("> ").append("is acceptable.").append("\n");
			} else {
				sb.append("<").append(line).append("> ").append("is not acceptable.").append("\n");
			}

		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isAcceptable(String s) {
		boolean hasConsonants = false;
		int cntConsonants = 0;
		int cntVowels = 0;

		// 첫 문자 처리
		char first = s.charAt(0);
		if (isConsonant(first)) {
			hasConsonants = true;
			cntConsonants++;
		} else {
			cntVowels++;
		}

		// 두 번째 문자부터 처리
		int len = s.length();
		for (int i = 1; i < len; i++) {
			char now = s.charAt(i);
			if (isConsonant(now)) {
				hasConsonants = true;
				cntConsonants++;
				cntVowels = 0;
			} else {
				cntVowels++;
				cntConsonants = 0;
			}

			if (cntConsonants >= 3 || cntVowels >= 3) {
				return false;
			}

			char prev = s.charAt(i - 1);
			if (now == prev) {
				if (now == 'e' || now == 'o') {
					continue;
				} else {
					return false;
				}
			}

		}

		if (hasConsonants) {
			return true;
		}

		else {
			return false;
		}
	}

	public static boolean isConsonant(char c) {
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			return true;
		}
		return false;
	}
}