// 11468KB, 80ms

package bj1918;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 중위 표기식 입력
		String infix = br.readLine();

		// 후위 표기식으로 변환
		String answer = getPostfix(infix);

		// 출력
		System.out.println(answer);

	} // end main

	/** 중위 표현식을 입력받아서 후위 표현식을 리턴 */
	public static String getPostfix(String infix) {
		char[] arr = infix.toCharArray();

		Stack<String> stack = new Stack<>();

		for (char c : arr) {
			// 피연산자를 만나면
			if (isDigit(c)) {
				// 스택에 곱하기나 나누기가 맨 위에 있다면 바로 처리
				if (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/"))) {
					String op = stack.pop();
					String left = stack.pop();
					String right = String.valueOf(c);

					String result = left + right + op;
					stack.push(result);
				}
				else {
					stack.push(String.valueOf(c));
				}
			}

			// 닫는 괄호를 만나면 여는 괄호까지를 처리
			else if (c == ')') {
				// 여는 괄호까지의 부분을 앞에서부터(스택 아래에서부터) 처리해야하니 일단 그 부분의 스택을 뒤집어서 revStack에 넣기
				Stack<String> revStack = new Stack<>();
				while (!stack.isEmpty() && !stack.peek().equals("(")) {
					revStack.push(stack.pop());
				}
				
				// 여는 괄호를 스택에서 제거
				stack.pop();
				
				// revStack에 담긴 것들을 앞에서부터 계산
				while (revStack.size() >= 3) {
					String left = revStack.pop();
					String op = revStack.pop();
					String right = revStack.pop();

					String result = left + right + op;
					revStack.push(result);
				}
				
				// 괄호 부분을 처리하고 났을 때, 스택에 곱하기나 나누기가 맨 위에 있다면 이것도 바로 처리
				if (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/"))) {
					String op = stack.pop();
					String left = stack.pop();
					String right = revStack.peek();

					String result = left + right + op;
					stack.push(result);
				}
				
				// 여는 괄호 ~ 닫는 괄호 사이 부분을 계산한 최종 결과를 원래의 스택에 넣는다.
				else {
					stack.push(revStack.peek());
				}
			}

			// 연산자를 만나면 그냥 넣는다.
			else {
				stack.push(String.valueOf(c));
			}
		
		} // end for c

		
		// 이 시점까지 오면 스택에는 +, -만 존재 가능 -> 이걸 앞에서부터 처리한다
		Stack<String> revStack = new Stack<>();
		while(!stack.isEmpty()) {
			revStack.push(stack.pop());
		}

		while (revStack.size() >= 3) {
			// 3개를 빼서 계산한 후 계산 결과를 넣는다.
			String left = revStack.pop();
			String op = revStack.pop();
			String right = revStack.pop();

			String result = left + right + op;
			revStack.push(result);
		}
		
		return revStack.peek();
	}

	/** A와 Z사이의 문자이면 true를 리턴한다 */
	public static boolean isDigit(char c) {
		if ('A' <= c && c <= 'Z') {
			return true;
		}

		return false;
	}

}