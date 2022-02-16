// 11520KB, 76ms

package bj1541;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();

		int answer = parse(s);

		System.out.println(answer);
	}

	public static int parse(String s) {
		if (s.length() == 0)
			return 0;

		StringBuilder sb = new StringBuilder();

		// '-'를 하나라도 만나면 그 이후로는 전부 뺄셈
		int length = s.length();
		boolean isSubtracting = false;
		int ret = 0;
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (c == '+') {
				if (isSubtracting) {
					ret -= Integer.parseInt(sb.toString());
					sb.setLength(0);
				} else {
					ret += Integer.parseInt(sb.toString());
					sb.setLength(0);
				}

			} else if (c == '-') {
				if (isSubtracting) {
					ret -= Integer.parseInt(sb.toString());
					sb.setLength(0);
				} else {
					ret += Integer.parseInt(sb.toString());
					sb.setLength(0);
				}
				sb.setLength(0);
				isSubtracting = true;
			} else {
				sb.append(c);
			}
		}

		if (isSubtracting) {
			ret -= Integer.parseInt(sb.toString());
		} else {
			ret += Integer.parseInt(sb.toString());
		}

		return ret;
	}
}