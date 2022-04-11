// 11832KB, 84ms

package bj19844;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), "- ");

		List<String> words = new ArrayList<>();
		while (st.hasMoreTokens()) {
			words.add(st.nextToken());
		}

		int cnt = 0;

		for (String word : words) {
			if (canBeDivided(word)) {
				cnt += 2;
			} else {
				cnt += 1;
			}
		}

		System.out.println(cnt);

	} // end main

	public static boolean canBeDivided(String word) {
		StringTokenizer st = new StringTokenizer(word, "'");

		String first = st.nextToken();

		if (!st.hasMoreTokens()) {
			return false;
		}

		String second = st.nextToken();

		if (first.equals("c") || first.equals("j") || first.equals("n") || first.equals("m") || first.equals("t")
				|| first.equals("s") || first.equals("l") || first.equals("d") || first.equals("qu")) {
			char c = second.charAt(0);
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'h') {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

}