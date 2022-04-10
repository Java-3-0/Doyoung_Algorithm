// 43052KB, 264ms

package bj17609;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			String text = br.readLine();
			if (isPalindrome(text)) {
				sb.append(0).append("\n");
			} else if (isSimilar(text)) {
				sb.append(1).append("\n");
			} else {
				sb.append(2).append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	/** text가 유사 회문인지 여부를 리턴 */
	public static boolean isSimilar(String text) {
		int len = text.length();
		int lastIdx = len - 1;
		int halfLen = len / 2;

		for (int i = 0; i < halfLen; i++) {
			if (text.charAt(i) != text.charAt(lastIdx - i)) {
				if (isPalindrome(text.substring(i, lastIdx - i))) {
					return true;
				} else if (isPalindrome(text.substring(i + 1, lastIdx - i + 1))) {
					return true;
				} else {
					return false;
				}
			}
		}

		return true;

	}

	/** text가 회문인지 여부를 리턴 */
	public static boolean isPalindrome(String text) {
		int len = text.length();
		int lastIdx = len - 1;
		int halfLen = len / 2;

		for (int i = 0; i < halfLen; i++) {
			if (text.charAt(i) != text.charAt(lastIdx - i)) {
				return false;
			}
		}

		return true;
	}

}