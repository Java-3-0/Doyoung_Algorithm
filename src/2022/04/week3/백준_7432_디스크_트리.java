// 24676KB, 224ms

package bj7432;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	static StringBuilder sb = new StringBuilder();

	/** 트라이 객체 */
	static class Trie {
		/** 트라이의 루트 */
		Node root;

		/** 생성자 */
		public Trie() {
			super();
			root = new Node();
		}

		/** 트라이에 경로 하나를 추가한다 */
		public void insert(String[] path) {
			Node cur = root;
			for (String directory : path) {
				if (cur.children.containsKey(directory)) {
					Node next = cur.children.get(directory);
					cur = next;
				} else {
					Node next = new Node();
					cur.children.put(directory, next);
					cur = next;
				}
			}

			cur.isTerminal = true;
		}

		/** 외부에서 retrieve를 편하게 호출하기 위한 오버로딩 함수 */
		public void retrieve() {
			retrieve(this.root, 0);
			return;
		}

		/** cur노드가 depth깊이에 있을 때, 이 노드부터 아래로 노드 정보들을 retrieve해서 sb에 담는다 */
		public void retrieve(Node cur, int depth) {
			while (!cur.children.isEmpty()) {
				Entry<String, Node> entry = cur.children.pollFirstEntry();
				String food = entry.getKey();
				Node next = entry.getValue();

				// 스트링빌더에 이 노드 정보 추가
				for (int i = 0; i < depth; i++) {
					sb.append(" ");
				}
				sb.append(food).append("\n");

				// 자식 노드로 재귀 호출
				retrieve(next, depth + 1);
			}

			return;
		}
	}

	/** 트라이의 노드 객체 */
	static class Node {
		/** 이 노드의 terminal 여부 */
		boolean isTerminal;
		/** 자식 노드 정보 */
		TreeMap<String, Node> children;

		/** 생성자 */
		public Node() {
			super();
			this.isTerminal = false;
			this.children = new TreeMap<>();
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 디렉토리 수 입력
		int N = Integer.parseInt(br.readLine());

		// 트라이 생성
		Trie trie = new Trie();

		// 각 디렉토리 경로를 처리
		for (int i = 0; i < N; i++) {
			// 디렉토리 경로 입력
			String[] path = br.readLine().split("\\\\");
			
			// 트라이에 추가
			trie.insert(path);
		}

		// 트라이에 담긴 정보를 retrieve해서 sb에 담는다
		trie.retrieve();

		// 출력
		System.out.print(sb.toString());

	} // end main

}