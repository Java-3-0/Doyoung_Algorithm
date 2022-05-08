// 11552KB, 80ms

package bj12400;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
	static Map<Character, Character> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 매핑 생성
		String[] befores = new String[3];
		String[] afters = new String[3];

		befores[0] = "ejp mysljylc kd kxveddknmc re jsicpdrysi";
		befores[1] = "rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd";
		befores[2] = "de kr kd eoya kw aej tysr re ujdr lkgc jv";

		afters[0] = "our language is impossible to understand";
		afters[1] = "there are twenty six factorial possibilities";
		afters[2] = "so it is okay if you want to just give up";

		for (int i = 0; i < 3; i++) {
			int len = befores[i].length();
			for (int k = 0; k < len; k++) {
				char c1 = befores[i].charAt(k);
				char c2 = afters[i].charAt(k);

				map.put(c1, c2);
			}
		}

		map.put('q', 'z');
		map.put('z', 'q');

		// 테스트케이스 수 입력
		int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			String cyphertext = br.readLine();
			String plaintext = decode(cyphertext);
			sb.append("Case #").append(tc).append(": ").append(plaintext).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static String decode(String cyphertext) {
		StringBuilder sb = new StringBuilder();
		
		for (char c : cyphertext.toCharArray()) {
			sb.append(map.getOrDefault(c, ' '));
		}
		
		return sb.toString();
	}

}