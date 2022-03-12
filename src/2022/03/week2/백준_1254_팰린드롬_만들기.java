// 11504KB, 76ms

package bj1254;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String S = br.readLine();

		int answer = Integer.MAX_VALUE;

		int len = S.length();
		for (int i = len - 1; i >= 0; i--) {
			String sub = S.substring(i);
			if (isPalindrome(sub)) {
				int subLen = sub.length();

				int totalLen = (len - subLen) * 2 + subLen;

				answer = Math.min(answer, totalLen);
			}
		}

		System.out.println(answer);
	}

	public static boolean isPalindrome(String s) {
		int len = s.length();
		for (int i = 0; i < len / 2; i++) {
			if (s.charAt(i) != s.charAt(len - 1 - i)) {
				return false;
			}
		}

		return true;
	}
}