package pg60058;

import java.util.Stack;

class Solution {
	public String solution(String s) {
		int len = s.length();

		// 1. 빈 문자열인 경우, 빈 문자열을 리턴
		if (len == 0)
			return "";

		// 2. 두 균형잡힌 괄호 문자열 u와 v로 분리
		int countOpen = 0;
		int countClose = 0;
		String u = "";
		String v = "";
		for (int i = 0; i < len; i++) {
			if (s.charAt(i) == '(') {
				countOpen++;
			} else {
				countClose++;
			}

			// 괄호가 균형이 맞게 되는 시점 i를 찾으면
			if (countOpen == countClose) {
				// 0번부터 i번까지 u가 되고
				u = s.substring(0, i + 1);
				// i+1번부터 끝까지 v가 된다
				v = s.substring(i + 1);
				break;
			}
		}

		// 3. 문자열 u가 올바른 괄호 문자열인 경우 v에 대해 재귀 호출한 것을 u에 붙여서 리턴
		if (isValid(u)) {
			return u + solution(v);
		}

		// 4. 문자열 u가 올바른 괄호 문자열이 아닌 경우
		else {
			StringBuilder sb = new StringBuilder();
			// 4-1. 빈 문자열에 '('를 붙인다.
			sb.append('(');
			// 4-2. v에 대해 재귀 호출한 결과를 붙인다.
			sb.append(solution(v));
			// 4-3. ')'를 다시 붙인다.
			sb.append(')');
			// 4-4. u의 첫번째와 마지막 문자를 제외한 나머지 문자들을 괄호 방향을 뒤집어서 붙인다.
			for (int i = 1; i < u.length() - 1; i++) {
				char c = u.charAt(i);
				if (c == '(') {
					sb.append(')');
				} else {
					sb.append('(');
				}
			}
			// 4-5. 생성된 문자열을 리턴한다.
			return sb.toString();
		}
	}

	/** 문자열 s가 올바른 괄호 문자열이면 true, 아니면 false를 리턴 */
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<Character>();

		for (char c : s.toCharArray()) {
			if (c == '(') {
				stack.push(c);
			} else {
				if (stack.isEmpty()) {
					return false;
				} else {
					stack.pop();
				}
			}
		}

		if (stack.isEmpty()) {
			return true;
		}
		return false;
	}
}