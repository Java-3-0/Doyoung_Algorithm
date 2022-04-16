// 160392KB, 720ms

package bj5670;

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

		/** 트라이에 단어 하나를 추가한다 */
		public void insert(String word) {
			Node cur = root;
			for (char c : word.toCharArray()) {
				if (cur.children.containsKey(c)) {
					Node next = cur.children.get(c);
					cur = next;
				} else {
					Node next = new Node();
					cur.children.put(c, next);
					cur = next;
				}
			}

			cur.isTerminal = true;
		}

		/** 단어를 쓰기 위해 필요한 버튼 누르기 횟수를 리턴 */
		public int getButtonCnt(String word) {
			// 첫 글자는 무조건 입력
			int ret = 1;
			Node cur = this.root.children.get(word.charAt(0));

			// 두 번째 글자부터 자동완성 시도
			int len = word.length();
			for (int i = 1; i < len; i++) {
				// 현재 글자
				char c = word.charAt(i);

				// 다음 글자로 여러 개가 올 수 있거나, 아예 여기가 단어의 끝일 수 있는 경우, 자동완성 불가능
				if (cur.children.size() != 1 || cur.isTerminal) {
					ret++;
				}

				// 노드 갱신
				Node next = cur.children.get(c);
				cur = next;
			}

			return ret;
		}

	}

	/** 트라이의 노드 객체 */
	static class Node {
		/** 이 노드의 terminal 여부 */
		boolean isTerminal;
		/** 자식 노드 정보 */
		TreeMap<Character, Node> children;

		/** 생성자 */
		public Node() {
			super();
			this.isTerminal = false;
			this.children = new TreeMap<>();
		}

		@Override
		public String toString() {
			return "Node [isTerminal=" + isTerminal + ", children=" + children + "]";
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String line = "";
		while ((line = br.readLine()) != null) {
			// 단어 수
			int N = Integer.parseInt(line);

			// 트라이 생성
			Trie trie = new Trie();

			// 단어 배열 메모리 할당
			String[] words = new String[N];

			// 단어 입력받으면서 트라이에 추가
			for (int i = 0; i < N; i++) {
				// 단어 입력
				words[i] = br.readLine();

				// 트라이에 추가
				trie.insert(words[i]);
			}

			// 각 단어에 대해 버튼 클릭 수 계산해서 그 합계를 구한다
			int sum = 0;
			for (String word : words) {
				int cnt = trie.getButtonCnt(word);
				sum += cnt;
			}

			// 평균 버튼 클릭 수 계산
			double avg = (double) sum / (double) N;

			// 출력 스트링빌더에 형식에 맞게 정답 추가
			sb.append(String.format("%.2f", avg)).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

}