// 16720KB, 132ms

package bj25178;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int ALPHABETS = 26;

	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		char[] text1 = br.readLine().toCharArray();
		char[] text2 = br.readLine().toCharArray();
		
		if (isDuramuri(text1, text2)) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}

	} // end main

	public static boolean isDuramuri(char[] text1, char[] text2) {
		// 첫 번째 조건
		int[] counts1 = getAlphabetCounts(text1);
		int[] counts2 = getAlphabetCounts(text2);
		for (int i = 0; i < ALPHABETS; i++) {
			if (counts1[i] != counts2[i]) {
				return false;
			}
		}

		// 두 번째 조건
		if (text1[0] != text2[0] || text1[N - 1] != text2[N - 1]) {
			return false;
		}

		// 세 번째 조건
		String vowelRemoved1 = getVowelRemovedString(text1);
		String vowelRemoved2 = getVowelRemovedString(text2);

		if (!vowelRemoved1.equals(vowelRemoved2)) {
			return false;
		}

		// 세 조건 모두 만족하면 성공
		return true;
	}

	public static int[] getAlphabetCounts(char[] text) {
		int[] counts = new int[ALPHABETS];

		for (char c : text) {
			int idx = c - 'a';
			counts[idx]++;
		}

		return counts;
	}

	public static String getVowelRemovedString(char[] text) {
		StringBuilder sb = new StringBuilder();

		for (char c : text) {
			if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') {
				sb.append(c);
			}
		}

		return sb.toString();
	}

}