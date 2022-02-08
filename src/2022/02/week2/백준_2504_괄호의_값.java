// 14340KB, 128ms

package baek2504;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String parenthesis = br.readLine();

		int answer = valOfParenthesis(parenthesis);
		System.out.println(answer);
	}

	public static int valOfParenthesis(String parenthesis) {
		Stack<Character> stack = new Stack<>();

		int ret = 0;
		
		for (int i = 0; i < parenthesis.length(); i++) {
			char c = parenthesis.charAt(i);
			
			
			switch (c) {
			case '(':
			case '[':
				stack.push(c);
				break;
			case ')':
			case ']':
				// 괄호가 잘 닫힌 경우
				if (!stack.isEmpty() && stack.peek() == closeToOpen(c)) {
					// 안에 아무것도 들어있지 않은 () 또는 []를 만날 때, 그 때까지 스택에 쌓인 괄호들의 점수를 모두 곱한 것을 리턴값에 더한다.
					if (i >= 1 && parenthesis.charAt(i - 1) == closeToOpen(c)) {
						ret += getProduct(stack);
					}
					
					// 스택에서 pop
					stack.pop();
				} 
				// 괄호가 잘못 닫힌 경우
				else {
					return 0;
				}
				break;
			default:
			}
		}

		// 괄호가 정당하면 계산한 점수를 그대로 리턴
		if (stack.isEmpty()) {
			return ret;
		}
		// 괄호가 잘못되었으면 0 리턴
		return 0;
	}

	public static int closeToOpen(char c) {
		switch (c) {
		case ')':
			return '(';
		case ']':
			return '[';
		}
		return -1;
	}

	/** 스택에 존재하는 괄호들의 점수 곱을 리턴 */
	public static int getProduct(Stack<Character> stack) {
		if (stack.isEmpty()) {
			return 0;
		}

		int product = 1;
		for (char c : stack) {
			if (c == '(') {
				product *= 2;
			}
			else if (c == '['){
				product *= 3;
			}
			else {
				return 0;
			}
		}

		return product;
	}
}