// 19124KB, 196ms

package baek10866;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

class Deque {
	LinkedList<Integer> linkedList = new LinkedList<> ();
	
	/** 맨 앞에 푸쉬 */
	void push_front (int x) {
		linkedList.addFirst(x);
	}
	
	/** 맨 뒤에 푸쉬 */
	void push_back (int x) {
		linkedList.addLast(x);
	}
	
	/** 맨 앞에서 pop해서 그 값을 리턴 */
	int pop_front () {
		if (empty()) {
			return -1;
		}
		
		int ret = linkedList.peekFirst();
		linkedList.removeFirst();
		return ret;
	}
	
	/** 맨 뒤에서 pop해서 그 값을 리턴 */
	int pop_back () {
		if (empty()) {
			return -1;
		}
		
		int ret = linkedList.peekLast();
		linkedList.removeLast();
		return ret;
	}
	
	/** 덱의 크기를 리턴 */
	int size () {
		return linkedList.size();
	}
	
	/** 덱이 비어있는지 여부를 리턴 */
	boolean empty () {
		return linkedList.isEmpty();
	}
	
	/** 덱의 맨 앞의 수를 리턴 */
	int front() {
		if (empty()) {
			return -1;
		}
		
		return linkedList.peekFirst();
	}
	
	/** 덱의 맨 뒤의 수를 리턴 */
	int back() {
		if (empty()) {
			return -1;
		}
		
		return linkedList.peekLast();
	}
}

public class Main {

	public static Deque deque;
	public static StringBuilder sb;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		sb = new StringBuilder();
		deque = new Deque();
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
		int numToPush;
		
		switch (commandType) {
		case "push_front":
			numToPush = Integer.parseInt(command[1]);
			deque.push_front(numToPush); // push 명령의 경우 출력은 없음
			break;
		case "push_back":
			numToPush = Integer.parseInt(command[1]);
			deque.push_back(numToPush); // push 명령의 경우 출력은 없음
			break;
		case "pop_front":
			sb.append(deque.pop_front()).append("\n");
			break;
		case "pop_back":
			sb.append(deque.pop_back()).append("\n");
			break;
		case "size":
			sb.append(deque.size()).append("\n");
			break;
		case "empty":
			sb.append(deque.empty() ? 1 : 0).append("\n");
			break;
		case "front":
			sb.append(deque.front()).append("\n");
			break;
		case "back":
			sb.append(deque.back()).append("\n");
			break;
		default:
		} // end switch
	}
}