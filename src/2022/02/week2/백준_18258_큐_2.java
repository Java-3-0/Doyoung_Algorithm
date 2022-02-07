// 341748KB, 1452ms

package baek18258;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

class Queue {
	LinkedList<Integer> linkedList = new LinkedList<> ();
	
	/** 맨 뒤에 푸쉬 */
	void push (int x) {
		linkedList.addLast(x);
	}
	
	/** 맨 앞에서 pop해서 그 값을 리턴 */
	int pop () {
		if (empty()) {
			return -1;
		}
		
		int ret = linkedList.peekFirst();
		linkedList.removeFirst();
		return ret;
	}
	
	/** 큐의 크기를 리턴 */
	int size () {
		return linkedList.size();
	}
	
	/** 큐가 비어있는지 여부를 리턴 */
	boolean empty () {
		return linkedList.isEmpty();
	}
	
	/** 큐의 맨 앞의 수를 리턴 */
	int front() {
		if (empty()) {
			return -1;
		}
		
		return linkedList.peekFirst();
	}
	
	/** 큐의 맨 뒤의 수를 리턴 */
	int back() {
		if (empty()) {
			return -1;
		}
		
		return linkedList.peekLast();
	}
}

public class Main {

	public static Queue queue;
	public static StringBuilder sb;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		sb = new StringBuilder();
		queue = new Queue();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

	
	public static void handleCommand(String[] command) {
		String commandType = command[0];

		switch (commandType) {
		case "push":
			int numToPush = Integer.parseInt(command[1]);
			queue.push(numToPush); // push 명령의 경우 출력은 없음
			break;
		case "pop":
			sb.append(queue.pop()).append("\n");
			break;
		case "size":
			sb.append(queue.size()).append("\n");
			break;
		case "empty":
			sb.append(queue.empty() ? 1 : 0).append("\n");
			break;
		case "front":
			sb.append(queue.front()).append("\n");
			break;
		case "back":
			sb.append(queue.back()).append("\n");
			break;
		default:
		} // end switch
	}
}
