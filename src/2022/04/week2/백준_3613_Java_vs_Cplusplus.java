// 11548KB, 80ms

package bj3613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();

		if (isJava(s)) {
			System.out.println(javaToC(s));
		} else if (isC(s)) {
			System.out.println(cToJava(s));
		} else {
			System.out.println("Error!");
		}

	} // end main

	public static boolean isJava(String s) {
		int len = s.length();
		if (!isSmall(s.charAt(0))) {
			return false;
		}

		for (int i = 1; i < len; i++) {
			char c = s.charAt(i);
			if (!isSmall(c) && !isCapital(c)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isC(String s) {
		int len = s.length();
		if (!isSmall(s.charAt(0))) {
			return false;
		}

		for (int i = 1; i < len; i++) {
			char c = s.charAt(i);
			if (isSmall(c)) {
				continue;
			} else if (isUnderBar(c)) {
				if (isUnderBar(s.charAt(i - 1))) {
					return false;
				}
			} else {
				return false;
			}
		}

		if (isUnderBar(s.charAt(len - 1))) {
			return false;
		}

		return true;
	}

	public static boolean isSmall(char c) {
		if ('a' <= c && c <= 'z') {
			return true;
		}
		return false;
	}

	public static boolean isCapital(char c) {
		if ('A' <= c && c <= 'Z') {
			return true;
		}
		return false;
	}

	public static boolean isUnderBar(char c) {
		if (c == '_') {
			return true;
		}
		return false;
	}

	public static String javaToC(String s) {
		StringBuilder sb = new StringBuilder();

		for (char c : s.toCharArray()) {
			if ('A' <= c && c <= 'Z') {
				char converted = (char) (c - 'A' + 'a');
				sb.append("_").append(converted);
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String cToJava(String s) {
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(s, "_");

		sb.append(st.nextToken());
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			char first = token.charAt(0);
			char converted = (char) (first - 'a' + 'A');
			sb.append(converted);

			for (int i = 1; i < token.length(); i++) {
				sb.append(token.charAt(i));
			}
		}

		return sb.toString();

	}

}