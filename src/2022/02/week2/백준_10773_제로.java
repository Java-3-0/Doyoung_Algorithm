// 23208KB, 256ms

package baek10773;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int K = Integer.parseInt(br.readLine());

		// 스택 생성
		Stack<Integer> stack = new Stack<Integer>();

		// 주어지는 정수대로 스택에 push or pop
		for (int i = 0; i < K; i++) {
			int num = Integer.parseInt(br.readLine());
			if (num == 0) {
				stack.pop();
			} else {
				stack.push(num);
			}
		}
		
		// 합계 출력
		System.out.println(getSum(stack));
	}

	/** 스택에 존재하는 모든 수의 합을 리턴 */
	public static int getSum(Stack<Integer> stack) {
		int sum = 0;
		while (!stack.empty()) {
			sum += stack.pop();
		}

		return sum;
	}
}
