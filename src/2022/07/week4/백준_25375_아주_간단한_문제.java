// 46528KB, 368ms

package boj25375;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			long a = Long.parseLong(st.nextToken());
			long b = Long.parseLong(st.nextToken());

			if (b % a == 0L && b / a >= 2L) {
				sb.append(1);
			} else {
				sb.append(0);
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}