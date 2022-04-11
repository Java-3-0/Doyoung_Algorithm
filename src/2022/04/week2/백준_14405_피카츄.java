// 11508KB, 76ms

package bj14405;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String S = br.readLine();

		if (isValid(S)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	} // end main

	public static boolean isValid(String S) {
		int len = S.length();

		int idx = 0;
		while (idx < len) {
			char c = S.charAt(idx);

			if (c == 'p') {
				if (idx + 1 < len && S.charAt(idx + 1) == 'i') {
					idx += 2;
				} else {
					return false;
				}
			} else if (c == 'k') {
				if (idx + 1 < len && S.charAt(idx + 1) == 'a') {
					idx += 2;
				} else {
					return false;
				}
			} else if (c == 'c') {
				if (idx + 2 < len && S.charAt(idx + 1) == 'h' && S.charAt(idx + 2) == 'u') {
					idx += 3;
				} else {
					return false;
				}
			} else {
				return false;
			}

		}

		return true;
	}

}