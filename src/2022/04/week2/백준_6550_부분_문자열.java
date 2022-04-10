// 16180KB, 116ms

package bj6550;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		String line = "";
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, " ");
			char[] s = st.nextToken().toCharArray();
			char[] t = st.nextToken().toCharArray();

			if (isPartialString(s, t)) {
				sb.append("Yes").append("\n");
			} else {
				sb.append("No").append("\n");
			}

		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isPartialString(char[] s, char[] t) {
		int sLen = s.length;
		int tLen = t.length;

		int idx1 = 0;
		int idx2 = 0;

		while (true) {
			if (idx1 == sLen) {
				return true;
			}

			if (idx2 == tLen) {
				return false;
			}

			if (s[idx1] == t[idx2]) {
				idx1++;
				idx2++;
			} else {
				idx2++;
			}
		}
	}

}