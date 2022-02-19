// 11528KB, 84ms

package bj1343;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final String IMPOSSIBLE = "-1";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();

		String result = fill(s);

		System.out.println(result);
	}

	public static String fill(String s) {
		StringBuilder sb = new StringBuilder();

		for (int idx = 0; idx < s.length();) {
			char c = s.charAt(idx);

			// '.'이면 그냥 그대로 '.'만 넣고 지나간다.
			if (c == '.') {
				sb.append('.');
				idx += 1;
			}

			else {
				// 4칸을 연속으로 채울 수 있다면 채운다.
				if (canFill4(s, idx)) {
					sb.append("AAAA");
					idx += 4;
					// 4칸은 안 되고 2칸은 되면 2칸을 채운다.
				} else if (canFill2(s, idx)) {
					sb.append("BB");
					idx += 2;
					// 4칸도 안 되고 2칸도 안 되면 실패다.
				} else {
					return IMPOSSIBLE;
				}
			}
		}

		return sb.toString();
	}

	/** s라는 String을 startIdx부터 4칸을 채울 수 있는지 여부를 리턴 */
	public static boolean canFill4(String s, int startIdx) {
		for (int delta = 0; delta < 4; delta++) {
			int idx = startIdx + delta;
			if (idx < 0 || idx >= s.length() || s.charAt(idx) != 'X') {
				return false;
			}
		}

		return true;
	}

	/** s라는 String을 startIdx부터 2칸을 채울 수 있는지 여부를 리턴 */
	public static boolean canFill2(String s, int startIdx) {
		for (int delta = 0; delta < 2; delta++) {
			int idx = startIdx + delta;
			if (idx < 0 || idx >= s.length() || s.charAt(idx) != 'X') {
				return false;
			}
		}

		return true;
	}
}