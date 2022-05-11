// 25532KB, 144ms

package bj12043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	static final long MOD = (long) (1e9 + 7);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			long answer = 1L;
			
			char[] text = br.readLine().toCharArray();
			int len = text.length;

			for (int i = 0; i < len; i++) {
				Set<Character> set = new HashSet<>();
				set.add(text[i]);
				if (0 <= i - 1) {
					set.add(text[i - 1]);
				}
				if (i + 1 < len) {
					set.add(text[i + 1]);
				}

				answer *= (long) (set.size());
				answer %= MOD;
				
			} // end for i
			
			sb.append("Case #").append(tc).append(": ").append(answer).append("\n");
			
		} // end for tc

		System.out.print(sb.toString());
		
	} // end main
}