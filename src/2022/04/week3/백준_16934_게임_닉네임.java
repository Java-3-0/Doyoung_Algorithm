package bj16934;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Main {
	/** 트라이 객체 */
	static class Trie {
		/** 트라이의 루트 */
		Node root;

		/** 생성자 */
		public Trie() {
			super();
			root = new Node();
		}

		/** 트라이에 아이디 하나를 추가한다 */
		public void insert(String userId) {
			Node cur = root;
			for (char c : userId.toCharArray()) {
				Node next;
				if (cur.children.containsKey(c)) {
					next = cur.children.get(c);
				} else {
					next = new Node();
					cur.children.put(c, next);
				}
				cur = next;
			}

			cur.isTerminal++;
		}

		/** userId에 해당하는 별칭을 구해서 리턴 */
		public String getNickname(String userId) {
			Node cur = root;
			StringBuilder sb = new StringBuilder();

			for (char c : userId.toCharArray()) {
				// 별칭에 이 문자 추가
				sb.append(c);
				
				// 이 문자가 자식 노드로 존재하면, 자식 노드로 이동해서 별칭을 더 만들어본다
				if (cur.children.containsKey(c)) {
					Node next = cur.children.get(c);
					cur = next;
				}

				// 이 문자가 자식 노드로 존재하지 않으면, 여기까지를 별칭으로 사용 가능
				else {
					return sb.toString();
				}
			}
			
			// 아이디 전체가 이미 있는 아이디인 경우, 이전까지 있던 개수 + 1을 한 값을 별칭 끝에 붙인다
			int num = cur.isTerminal + 1;
			if (num >= 2) {
				sb.append(num);
			}
			
			return sb.toString();
		}

	}

	/** 트라이의 노드 객체 */
	static class Node {
		/** 이 노드의 terminal 여부인데, 단어가 몇 개째 중복인지 알기 위해 int로 관리 */
		int isTerminal;
		/** 자식 노드 정보 */
		TreeMap<Character, Node> children;

		/** 생성자 */
		public Node() {
			super();
			this.isTerminal = 0;
			this.children = new TreeMap<>();
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 닉네임 수
		int N = Integer.parseInt(br.readLine());

		// 트라이 생성
		Trie trie = new Trie();

		// 단어 입력받으면서 트라이에 추가
		for (int i = 0; i < N; i++) {
			// 단어 입력
			String userId = br.readLine();

			// 닉네임 구하기
			String nickname = trie.getNickname(userId);

			// 트라이에 추가
			trie.insert(userId);

			// 출력 스트링빌더에 답 추가
			sb.append(nickname).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

}