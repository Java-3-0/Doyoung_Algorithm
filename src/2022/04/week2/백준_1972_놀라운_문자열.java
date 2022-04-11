// 11912KB, 84ms

package bj1972;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String line = "";

		while (!(line = br.readLine()).equals("*")) {
			sb.append(line).append(" is");

			if (!isSurprising(line)) {
				sb.append(" NOT");
			}

			sb.append(" surprising.").append("\n");

		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isSurprising(String s) {
		StringBuilder sb = new StringBuilder();
		Set<String> set = new HashSet<>();

		int len = s.length();
		for (int d = 0; d <= len - 2; d++) {
			set.clear();
			for (int i = 0; i < len; i++) {
				int j = i + 1 + d;
				if (j < len) {
					sb.setLength(0);
					sb.append(s.charAt(i)).append(s.charAt(j));
					String tmp = sb.toString();
					if (set.contains(tmp)) {
						return false;
					} else {
						set.add(tmp);
					}
				}
			}
		}

		return true;
	}

}