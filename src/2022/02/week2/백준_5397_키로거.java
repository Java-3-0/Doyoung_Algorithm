// 123324KB, 660ms

package baek5397;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Node {
	char character;
	Node prev;
	Node next;

	public Node(char character) {
		super();
		this.character = character;
	}
}

class MyList {
	/** 의미없는 값의 노드로 head를 나타내기만 함 */
	Node head;
	/** 의미없는 값의 노드로 tail을 나타내기만 함 */
	Node tail;
	/** 커서의 위치 : cursor = node이면 커서는 node 오른쪽에 존재*/ 
	Node cursor;

	public MyList() {
		super();
		head = new Node('0');
		tail = new Node('0');
		cursor = head;
		
		head.next = tail;
		tail.prev = head;
	}

	/** 커서를 왼쪽으로 옮김 */
	void moveCursorLeft() {
		// 이미 맨 왼쪽인 경우를 제외하고 실행
		if (cursor != head) {
			cursor = cursor.prev;
		}
	}

	/** 커서를 오른쪽으로 옮김 */
	void moveCursorRight() {
		// 이미 맨 오른쪽인 경우를 제외하고 실행
		if (cursor.next != tail) {
			cursor = cursor.next;
		}
	}

	/** 커서 위치에 문자 삽입 */
	void add(char c) {
		// 삽입할 노드
		Node node = new Node(c);
		// 삽입할 노드의 이전 노드가 될 노드
		Node prevNode = cursor;
		// 삽입할 노드의 다음 노드가 될 노드
		Node nextNode = cursor.next;

		// 노드들의 연결 관계 갱신
		node.next = nextNode;
		node.prev = prevNode;

		prevNode.next = node;
		nextNode.prev = node;

		// 커서 갱신
		cursor = node;
	}

	/** 커서 위치에서 문자 제거 */
	void backspace() {
		// 지울 노드
		Node node = cursor;

		// 지울 노드가 존재하면 삭제
		if (node != head && node != tail) {
			Node prevNode = node.prev;
			Node nextNode = node.next;
			
			// 노드들의 연결 관계 갱신
			prevNode.next = nextNode;
			nextNode.prev = prevNode;

			// 커서 갱신
			cursor = prevNode;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Node node = head.next; node != tail; node = node.next) {
			sb.append(node.character);
		}
		sb.append("\n");

		return sb.toString();
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			String s = br.readLine();

			MyList list = new MyList();

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				switch (c) {
				case '<':
					list.moveCursorLeft();
					break;
				case '>':
					list.moveCursorRight();
					break;
				case '-':
					list.backspace();
					break;
				default:
					list.add(c);
					break;
				}
			}

			System.out.print(list.toString());
		}
	}
}
