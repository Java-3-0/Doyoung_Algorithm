// 324152KB, 980ms

package bj4256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 노드 수 입력
			int N = Integer.parseInt(br.readLine());
			
			// preorder 입력
			int[] pre = new int[N];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				pre[i] = Integer.parseInt(st.nextToken());
			}
			
			// inorder 입력
			int[] in = new int[N];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				in[i] = Integer.parseInt(st.nextToken());
			}
			
			String answer = getPostOrder(pre, in, 0, 0, pre.length - 1);
			
			System.out.println(answer);
		}

		
	} // end main
	
	/**
	 * 후위 순회 결과를 리턴
	 * @param pre : preOrder
	 * @param in : inOrder
	 * @param rootIdx : preOrder에서 root의 idx
	 * @param startIdx : inOrder에서 시작 idx
	 * @param endIdx : inOrder에서 끝 idx
	 * @return
	 */
	public static String getPostOrder(int[] pre, int[] in, int rootIdx, int startIdx, int endIdx) {
		if (startIdx < 0 || startIdx >= pre.length || endIdx < 0 || endIdx >= pre.length) {
			return "";
		}
		
		if (startIdx == endIdx) {
			return String.valueOf(in[startIdx]) + " ";
		}
		
		StringBuilder sb = new StringBuilder();
		
		// pre에서 루트를 찾는다.
		int root = pre[rootIdx];
		
		// in에서 루트의 위치를 찾는다.
		// 그 이전까지가 왼쪽 subtree, 그 이후부터가 오른쪽 subtree가 된다.
		for (int i = startIdx; i <= endIdx; i++) {
			if (in[i] == root) {
				int leftSubTreeSize = i - startIdx;
				
				sb.append(getPostOrder(pre, in, rootIdx + 1, startIdx, i - 1));
				sb.append(getPostOrder(pre, in, rootIdx + 1 + leftSubTreeSize,  i + 1, endIdx));
				sb.append(root).append(" ");
				
				break;
			}
		}
		
		return sb.toString();
	}
}