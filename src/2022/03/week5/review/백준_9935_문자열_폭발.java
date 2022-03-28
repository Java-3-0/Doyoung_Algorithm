// 100196KB, 532ms

package bj9935;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
	static final int MAX_LEN = 1000000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 문자열 입력
		char[] text = br.readLine().toCharArray();
		char[] explode = br.readLine().toCharArray();

		String remaining = getRemainingText(text, explode);

		if (remaining.length() == 0) {
			System.out.println("FRULA");
		} else {
			System.out.println(remaining);
		}

	} // end main

	public static String getRemainingText(char[] text, char[] explode) {
		// 폭발 문자열의 길이
		int lenExp = explode.length;
		// 문자를 하나씩 담을 스택
		Stack<Character> stack = new Stack<>();
		// 문자가 몇 개까지 연속으로 매칭되었는지를 담을 스택
		Stack<Integer> continuousMatches = new Stack<>();

		// 초기 상태
		continuousMatches.push(-1);

		for (char c : text) {
			// 스택에 문자열을 push
			stack.push(c);

			// continuousMatches에 연속적으로 매칭된 문자 idx를 push
			int top = continuousMatches.peek();
			if (c == explode[top + 1]) {
				continuousMatches.push(top + 1);
			} else if (c == explode[0]) {
				continuousMatches.push(0);
			} else {
				continuousMatches.push(-1);
			}

			// lenExp개 만큼이 연속적으로 매칭되었다면 lenExp개를 pop
			if (continuousMatches.peek() == lenExp - 1) {
				for (int i = 0; i < lenExp; i++) {
					stack.pop();
					continuousMatches.pop();
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			sb.append(stack.pop());
		}

		return sb.reverse().toString();
	}

}