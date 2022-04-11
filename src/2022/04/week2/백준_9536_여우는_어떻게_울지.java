// 11788KB, 84ms

package bj9536;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static Set<String> others = new HashSet<String>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			others.clear();

			String[] sounds = br.readLine().split(" ");

			while (true) {
				String line = br.readLine();
				if (line.equals("what does the fox say?")) {
					break;
				}

				st = new StringTokenizer(line, " ");
				st.nextToken();
				st.nextToken();
				others.add(st.nextToken());
			}

			for (String s : sounds) {
				if (!others.contains(s)) {
					sb.append(s).append(" ");
				}
			}

			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}