// 11628KB, 80ms

package bj21314;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		br.close();

		// 가장 큰 민겸수와 가장 작은 민겸수 계산
		String maximum = getMaxNumber(s);
		String minimum = getMinNumber(s);

		// 정답 출력
		System.out.println(maximum);
		System.out.println(minimum);
	}

	/** 가능한 최소 민겸수를 문자열 형태로 리턴 */
	public static String getMinNumber(String s) {
		// MMM...M은 최대한 길게 쓰고, K는 하나씩 따로 처리한다
		int len = s.length();

		StringBuilder sb = new StringBuilder();
		Stack<Character> stack = new Stack<>();

		for (int idx = 0; idx < len; idx++) {
			char c = s.charAt(idx);
			// M은 스택에 쌓는다.
			if (c == 'M') {
				stack.push(c);
			} else {
				int size = stack.size();

				// 쌓인 MMM... 부분은 100...으로 처리하고, K는 5로 처리한다.
				for (int stackIdx = 0; stackIdx < size; stackIdx++) {
					if (stackIdx == 0) {
						sb.append('1');
					} else {
						sb.append('0');
					}
				}
				sb.append('5');

				// 쌓인 스택을 비운다
				while (!stack.isEmpty()) {
					stack.pop();
				}
			}
		}

		// 다 처리하고 나서 스택에 남은 것들 처리
		if (!stack.isEmpty()) {
			int size = stack.size();

			// 남은 MMM...M은 100...0으로 처리한다.
			sb.append('1');
			for (int stackIdx = 0; stackIdx < size - 1; stackIdx++) {
				sb.append('0');
			}

		}

		return sb.toString();
	}

	/** 가능한 최대 민겸수를 문자열 형태로 리턴 */
	public static String getMaxNumber(String s) {
		// MMM...K를 최대한 길게 쓴다.
		int len = s.length();

		StringBuilder sb = new StringBuilder();
		Stack<Character> stack = new Stack<>();

		for (int idx = 0; idx < len; idx++) {
			char c = s.charAt(idx);
			// M은 스택에 쌓는다.
			if (c == 'M') {
				stack.push(c);
			} else {
				int size = stack.size();

				// 쌓인 MMM...K 부분은 5000...으로 처리한다.
				sb.append('5');
				for (int stackIdx = 0; stackIdx < size; stackIdx++) {
					sb.append('0');
				}

				// 쌓인 스택을 비운다
				while (!stack.isEmpty()) {
					stack.pop();
				}
			}
		}

		// 다 처리하고 나서 스택에 남은 것들 처리
		if (!stack.isEmpty()) {
			int size = stack.size();

			// 남은 MMM...M은 모두 111...1로 처리한다.
			for (int stackIdx = 0; stackIdx < size; stackIdx++) {
				sb.append('1');
			}
		}

		return sb.toString();
	}
}