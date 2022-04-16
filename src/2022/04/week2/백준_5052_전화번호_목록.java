// 200560KB, 952ms

package bj5052;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {
	/** 트라이 자료구조를 나타내는 객체 */
	static class Trie {
		/** 트라이의 루트 */
		Node root;

		/** 생성자 */
		public Trie() {
			super();
			this.root = new Node();
		}

		/** 트라이에 phone 문자열을 넣는다 */
		public void insert(String phone) {
			Node cur = this.root;
			for (char c : phone.toCharArray()) {
				// 새로 추가되는 child인 경우
				if (!cur.children.containsKey(c)) {
					Node next = new Node();
					cur.children.put(c, next);
					cur = next;
				}
				// 기존에 존재하는 child인 경우
				else {
					Node next = cur.children.get(c);
					cur = next;
				}
			}

			cur.isTerminal = true;

			return;
		}

		/** phone의 모든 문자가 이미 트라이에 들어있으면 true, 아니면 false를 리턴 */
		public boolean find(String phone) {
			Node cur = this.root;
			for (char c : phone.toCharArray()) {
				if (!cur.children.containsKey(c)) {
					return false;
				}
				// 기존에 존재하는 child인 경우
				else {
					Node next = cur.children.get(c);
					cur = next;
				}
			}

			return true;
		}

	}

	/** 트라이의 노드를 나타내는 객체 */
	static class Node {
		/** 단어의 끝인지 여부를 나타내는 boolean 값 */
		boolean isTerminal;
		/** 자식 노드들의 Map */
		Map<Character, Node> children;

		public Node() {
			super();
			this.isTerminal = false;
			this.children = new HashMap<>();
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 테스트케이스 수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		// 테스트케이스 수만큼 반복
		for (int tc = 1; tc <= TESTS; tc++) {
			// 전화번호 개수 입력
			int N = Integer.parseInt(br.readLine());

			// 전화번호 입력
			String[] phoneNumbers = new String[N];
			for (int i = 0; i < N; i++) {
				phoneNumbers[i] = br.readLine();
			}

			// 일관성 여부 판단 후 출력 스트링빌더에 결과 추가
			if (isConsistent(phoneNumbers)) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}

		} // end for tc

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 파라미터로 전화번호 목록이 일관성 있는 목록인지 여부를 리턴 */
	public static boolean isConsistent(String[] phoneNumbers) {
		// 전화번호들 사전 역순 정렬
		Arrays.sort(phoneNumbers, Collections.reverseOrder());

		// 트라이 생성
		Trie trie = new Trie();

		// 각 전화번호가 일관성있는지 판단 후, 일관성 있다면 트라이에 넣는다
		for (String phone : phoneNumbers) {
			if (trie.find(phone)) {
				return false;
			} else {
				trie.insert(phone);
			}
		}

		return true;
	}

}