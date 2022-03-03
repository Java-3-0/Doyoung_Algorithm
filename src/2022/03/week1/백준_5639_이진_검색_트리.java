// 18208KB, 316ms

package bj5639;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static int MAX_NODES = 10000;
	static int MAX_KEY = 1000000;

	static List<Integer> pre = new ArrayList<>(MAX_NODES);
	static List<Integer> post = new ArrayList<>(MAX_NODES);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// 입력
		String line = "";
		while ((line = br.readLine()) != null) {
			pre.add(Integer.parseInt(line));
		}

		// 후위 순회 결과를 만든다.
		makePostOrder(0, pre.size() - 1);
		
		// 출력 스트링빌더에 추가
		for (int num : post) {
			sb.append(num).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());
		
	} // end main

	/** pre[startIdx]부터 pre[endIdx]까지를 후위 순회한 결과를 post에 넣는다 */
	public static void makePostOrder(int startIdx, int endIdx) {
		// base case
		if (startIdx > endIdx) {
			return;
		}

		if (startIdx == endIdx) {
			post.add(pre.get(startIdx));
			return;
		}
		
		// 루트
		int root = pre.get(startIdx);
		
		// 루트보다 큰 값 중 가장 먼저 만나는 값의 위치를 파악
		int rightSubTreeStartIdx = endIdx + 1;
		for (int idx = startIdx + 1; idx <= endIdx; idx++) {
			if (pre.get(idx) > root) {
				rightSubTreeStartIdx = idx;
				break;
			}
		}

		// 그 위치 이전까지가 left subtree
		makePostOrder(startIdx + 1, rightSubTreeStartIdx - 1);
		// 그 위치부터가 right subtree
		makePostOrder(rightSubTreeStartIdx, endIdx);
		// 양쪽 서브트리 순회 후에 루트 순회
		post.add(root);
	}
}