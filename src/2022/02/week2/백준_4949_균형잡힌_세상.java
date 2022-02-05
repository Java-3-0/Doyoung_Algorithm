// 21164KB, 252ms

// package baek4949;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력의 종료조건인 "." 하나로만 이루어진 줄을 만날 때까지 문자열을 입력받아서 sbInput에 추가
		String line = "";
		while (!((line = br.readLine()).equals("."))) {
			sb.append(line);
		}

		// sbInput에 담긴 내용을 문자열로 변환
		String inputStr = sb.toString();
		sb.setLength(0);
		// "."을 기준으로 tokenize
		String[] strings = inputStr.split("\\.");

		// 문자열의 괄호 올바름 여부를 sbOutput에 추가
		for (int i = 0; i < strings.length; i++) {
			if (isValid(strings[i])) {
				sb.append("yes\n");
			} else {
				sb.append("no\n");
			}
		}

		// 출력
		System.out.print(sb.toString());
	}

	/**
	 * String의 괄호 관계가 올바른지 여부를 리턴
	 * 
	 * @param s : 괄호의 올바름을 파악할 String
	 * @return 괄호가 올바르면 true, 아니면 false
	 */
	public static boolean isValid(String s) {
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < s.length(); i++) {
			// 괄호를 열 때 -> 소괄호이면 1, 대괄호이면 2를 스택에 push
			if (s.charAt(i) == '(') {
				stack.push(1);
			} else if (s.charAt(i) == '[') {
				stack.push(2);
			}

			// 괄호를 닫을 때 -> 스택이 비어있거나 스택의 top이 다른 종류의 괄호라면 괄호 관계는 올바르지 않음. 그렇지 않다면 pop
			else if (s.charAt(i) == ')') {
				if (stack.empty() || stack.peek() != 1) {
					return false;
				}
				stack.pop();
			} else if (s.charAt(i) == ']') {
				if (stack.empty() || stack.peek() != 2) {
					return false;
				}
				stack.pop();
			}
			// 괄호가 아닌 문자는 아무 것도 하지 않고 무시
		}

		// 모두 수행한 이후 스택이 비어 있어야 올바른 괄호 문자열
		if (stack.empty()) {
			return true;
		}
		return false;
	}
}
