// 17144KB, 188ms

// package baek10828;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Stack {
	static final int MAX_SIZE = 10000;

	/** Stack에 담기는 원소들을 저장할 공간이 되는 배열 */
	int[] arr = new int[MAX_SIZE];
	/** 스택의 맨 위 원소의 index */
	int topIdx = -1;

	/** 스택에 정수 x를 넣음 */
	void push(int x) {
		arr[++topIdx] = x;
	}

	/** 스택의 맨 위 값을 꺼내서 리턴 */
	int pop() {
		if (topIdx == -1) {
			return -1;
		}
		return arr[topIdx--];
	}

	/** 스택의 크기를 리턴 */
	int size() {
		return topIdx + 1;
	}

	/** 스택이 비어 있다면 1, 아니면 0을 리턴 */
	int empty() {
		if (topIdx == -1) {
			return 1;
		}
		return 0;
	}

	/** 스택의 맨 위 값을 꺼내지는 않고 리턴 */
	int top() {
		if (topIdx == -1) {
			return -1;
		}
		return arr[topIdx];
	}

}

public class Main {
	public static Stack stack;
	public static StringBuilder sb;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		stack = new Stack();

		int numCommands = Integer.parseInt(br.readLine());
		// 명령 개수만큼 실행
		for (int i = 0; i < numCommands; i++) {
			// 명령을 입력받아서 공백으로 tokenize
			String[] command = br.readLine().split(" ");
			// 명령 처리 후 StringBuilder에 쌓음
			handleCommand(command);
		}

		System.out.print(sb.toString());
	}

	/**
	 * 하나의 명령을 처리해서 결과를 StringBuilder에 추가
	 * 
	 * @param command : command[0]에는 명령의 종류가, command[1]은 push 명령에 한해서만 존재하며 넣을 정수가 들어 있는
	 *                String 배열
	 */
	public static void handleCommand(String[] command) {
		String commandType = command[0];

		switch (commandType) {
		case "push":
			int numToPush = Integer.parseInt(command[1]);
			stack.push(numToPush); // push 명령의 경우 출력은 없음
			break;
		case "pop":
			sb.append(stack.pop()).append("\n");
			break;
		case "size":
			sb.append(stack.size()).append("\n");
			break;
		case "empty":
			sb.append(stack.empty()).append("\n");
			break;
		case "top":
			sb.append(stack.top()).append("\n");
			break;
		default:
		} // end switch
	}
}
