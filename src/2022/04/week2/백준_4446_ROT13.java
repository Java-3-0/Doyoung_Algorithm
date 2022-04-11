// 11532KB, 84ms

package bj4446;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static final int CNT_VOWELS = 6;
	static final int CNT_CONSONANTS = 20;
	static final int ALPHABETS = 26;

	static List<Character> vowels = new ArrayList<>();
	static List<Character> consonants = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		makeLists();

		String line = "";
		while ((line = br.readLine()) != null) {
			char[] plaintext = line.toCharArray();
			char[] cyphertext = encrypt(plaintext);

			for (char c : cyphertext) {
				sb.append(c);
			}
			
			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static void makeLists() {
		vowels.add('a');
		vowels.add('i');
		vowels.add('y');
		vowels.add('e');
		vowels.add('o');
		vowels.add('u');

		consonants.add('b');
		consonants.add('k');
		consonants.add('x');
		consonants.add('z');
		consonants.add('n');
		consonants.add('h');
		consonants.add('d');
		consonants.add('c');
		consonants.add('w');
		consonants.add('g');
		consonants.add('p');
		consonants.add('v');
		consonants.add('j');
		consonants.add('q');
		consonants.add('t');
		consonants.add('s');
		consonants.add('r');
		consonants.add('l');
		consonants.add('m');
		consonants.add('f');

	}

	public static char[] encrypt(char[] plaintext) {
		int len = plaintext.length;

		char[] ret = new char[len];
		for (int i = 0; i < len; i++) {
			char c = plaintext[i];
			// 모음
			if (isVowel(c)) {
				ret[i] = convertVowel(c);
			}

			// 자음
			else if (isConsonant(c)) {
				ret[i] = convertConsonant(c);
			}

			// 나머지
			else {
				ret[i] = c;
			}
		}

		return ret;
	}

	public static boolean isVowel(char c) {
		char small = c;

		if ('A' <= c && c <= 'Z') {
			small = (char) (c - 'A' + 'a');
		}

		if (vowels.contains(small)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isConsonant(char c) {
		char small = c;

		if ('A' <= c && c <= 'Z') {
			small = (char) (c - 'A' + 'a');
		}

		if (consonants.contains(small)) {
			return true;
		} else {
			return false;
		}

	}

	public static char convertConsonant(char c) {
		char small = c;

		boolean isBig = false;
		if ('A' <= c && c <= 'Z') {
			isBig = true;
			small = (char) (c - 'A' + 'a');
		}

		for (int i = 0; i < CNT_CONSONANTS; i++) {
			if (consonants.get(i) == small) {
				char converted = consonants.get((i + 10) % CNT_CONSONANTS);

				if (isBig) {
					return (char) (converted - 'a' + 'A');
				} else {
					return converted;
				}
			}
		}

		return '\0';

	}

	public static char convertVowel(char c) {
		char small = c;

		boolean isBig = false;
		if ('A' <= c && c <= 'Z') {
			isBig = true;
			small = (char) (c - 'A' + 'a');
		}

		for (int i = 0; i < CNT_VOWELS; i++) {
			if (vowels.get(i) == small) {
				char converted = vowels.get((i + 3) % CNT_VOWELS);

				if (isBig) {
					return (char) (converted - 'a' + 'A');
				} else {
					return converted;
				}
			}
		}

		return '\0';

	}

}