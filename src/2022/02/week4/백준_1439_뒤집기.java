// 11484KB, 88ms

package bj1439;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		String s = br.readLine();
		
		// 최소 연산수 계산
		int answer = getMinOperations(s);
		
		// 출력
		System.out.println(answer);
	}

	/** 주어진 문자열을 000...0 또는 111...1로 만들기 위해 필요한 최소 연산 수를 리턴 */
	public static int getMinOperations(String s) {
		int len = s.length();
		int ret = 0;
		
		// 첫 문자와 다른 것들을 세는데, 같은 문자가 이어지는 것은 한 번만 센다.
		int firstChar = s.charAt(0);
		for (int i = 1; i < len; i++) {
			if (s.charAt(i) != firstChar && s.charAt(i) != s.charAt(i - 1)) {
				ret++;
			}
		}

		return ret;
		
	}
}