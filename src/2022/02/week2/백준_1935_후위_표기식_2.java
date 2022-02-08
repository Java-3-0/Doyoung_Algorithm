// 14548KB, 132ms

package baek1935;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		String expression = br.readLine();

		// (미지수 이름 -> 값)의 매핑 생성
		Map<Character, Double> variableToValue = new HashMap<>();
		for (int i = 0; i < N; i++) {
			double val = Double.parseDouble(br.readLine());
			char variable = (char) ('A' + i);
			variableToValue.put(variable, val);
		}

		Stack<Double> stack = new Stack<>();

		for (char c : expression.toCharArray()) {
			switch (c) {
			// 연산자인 경우, 스택에서 2개를 pop해서 각 연산을 수행한 후 다시 스택에 push
			case '+':
			case '-':
			case '*':
			case '/':
				double num2 = stack.pop();
				double num1 = stack.pop();
				double result = calcOperation(num1, num2, c);
				stack.push(result);
				break;
			// 피연산자인 경우, 스택에 push
			default:
				stack.push(variableToValue.get(c));
			}
		}

		double answer = stack.peek();
		System.out.printf("%.2f\n", answer);
	}

	/**
	 * 하나의 연산 결과를 계산
	 * 
	 * @param num1     : 피연산자1
	 * @param num2     : 피연산자2
	 * @param operator : 연산자
	 * @return 연산의 결과
	 */
	public static double calcOperation(double num1, double num2, char operator) {
		switch (operator) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':
			return num1 / num2;
		}

		return -1;
	}
}