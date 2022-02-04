// 33560KB, 408ms

package baek1874;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력을 받아 목적 수열을 Queue로 생성
		int length = Integer.parseInt(br.readLine());
		Queue<Integer> targetQueue = new LinkedList<Integer>();

		for (int i = 0; i < length; i++) {
			targetQueue.add(Integer.parseInt(br.readLine()));
		}

		// 1부터 (length-1)까지의 수를 오름차순으로 꺼내오기 위한 Queue 생성
		Queue<Integer> ascQueue = new LinkedList<Integer>();
		for (int i = 1; i <= length; i++) {
			ascQueue.add(i);
		}

		// 수를 push, pop할 Stack 생성
		Stack<Integer> stack = new Stack<Integer>();

		// 목적 수열을 다 찾을 때까지 반복
		while (!targetQueue.isEmpty()) {
			// 스택이 비어 있다면 다음으로 넣어야 할 수를 하나 push
			if (stack.empty()) {
				stack.push(ascQueue.poll());
				sb.append("+\n");
			}

			// 스택의 top과 다음으로 꺼내야 하는 수가 같으면 pop, 스택의 top이 더 작으면 다음 수를 push, 스택의 top이 더 크면 불가능
			int targetNum = targetQueue.peek();
			int stackTop = stack.peek();
			if (stackTop == targetNum) {
				stack.pop();
				targetQueue.poll();
				sb.append("-\n");
			} else if (stackTop < targetNum) {
				stack.push(ascQueue.poll());
				sb.append("+\n");
			} else {
				sb.setLength(0);
				sb.append("NO\n");
				break;
			}
		} // end while

		// 결과 출력
		System.out.println(sb.toString());

	} // end main
}
