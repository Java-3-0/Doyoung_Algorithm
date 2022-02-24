// 142740KB, 152ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final char ROOT = 'A';
	static final int MAX_NODENUM = (int)Math.pow(2, 26) - 1;

	static class BinaryTree {
		char[] arr;
		Map<Character, Integer> charToIdx = new HashMap<>();
		
		/** 생성자 */
		public BinaryTree() {
			super();
			arr = new char[MAX_NODENUM + 1];
			arr[1] = ROOT;
			charToIdx.put(ROOT, 1);
		}

		/** idx의 왼쪽 자녀를 추가함 */
		public void addLeft(int idx, char value) {
			arr[leftIdx(idx)] = value;
			charToIdx.put(value, leftIdx(idx));
		}

		/** idx의 오른쪽 자녀를 추가함 */
		public void addRight(int idx, char value) {
			arr[rightIdx(idx)] = value;
			charToIdx.put(value, rightIdx(idx));
		}

		/** 트리에서 특정 문자가 담겨 있는 인덱스를 리턴 */
		public int findCharacter(char c) {
			return charToIdx.get(c);
		}

		/** root의 인덱스가 1일 때, 파라미터로 주어진 idx의 왼쪽 자녀의 인덱스를 리턴 */
		public int leftIdx(int idx) {
			return idx * 2;
		}

		/** root의 인덱스가 1일 때, 파라미터로 주어진 idx의 오른쪽 자녀의 인덱스를 리턴 */
		public int rightIdx(int idx) {
			return idx * 2 + 1;
		}

		/** root의 인덱스가 1일 때, 파라미터로 주어진 idx의 오른쪽 자녀의 인덱스를 리턴 */
		public int parentIdx(int idx) {
			return idx / 2;
		}

		public String preOrder(int idx) {
			if (arr[idx] == '.') {
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(arr[idx]);
			sb.append(preOrder(leftIdx(idx)));
			sb.append(preOrder(rightIdx(idx)));
			
			return sb.toString();
		}
		
		public String inOrder(int idx) {
			if (arr[idx] == '.') {
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(inOrder(leftIdx(idx)));
			sb.append(arr[idx]);
			sb.append(inOrder(rightIdx(idx)));
			
			return sb.toString();
		}
		
		public String postOrder(int idx) {
			if (arr[idx] == '.') {
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(postOrder(leftIdx(idx)));
			sb.append(postOrder(rightIdx(idx)));
			sb.append(arr[idx]);
			
			return sb.toString();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		BinaryTree tree = new BinaryTree();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			char parent = st.nextToken().charAt(0);
			char leftChild = st.nextToken().charAt(0);
			char rightChild = st.nextToken().charAt(0);

			int parentIdx = tree.findCharacter(parent);
			tree.addLeft(parentIdx, leftChild);
			tree.addRight(parentIdx, rightChild);
		}
		
		System.out.println(tree.preOrder(1));
		System.out.println(tree.inOrder(1));
		System.out.println(tree.postOrder(1));
	} // end main

}