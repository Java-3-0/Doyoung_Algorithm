// 49872KB, 1372ms

package bj2263;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N;
	static StringBuilder sbPre = new StringBuilder();
	static int[] post;
	static int[] in;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		N = Integer.parseInt(br.readLine());
		in = new int[N];
		post = new int[N];

		// inorder 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			in[i] = Integer.parseInt(st.nextToken());
		}

		// postorder 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			post[i] = Integer.parseInt(st.nextToken());
		}

		// preorder 계산
		preorder(0, N - 1, 0, N - 1);

		// 출력
		System.out.println(sbPre.toString());

	} // end main

	/** preorder 순회 순서를 구해서 sbPre에 넣는다 */
	public static void preorder(int inLeft, int inRight, int postLeft, int postRight) {
		// base case
		if (inLeft > inRight || postLeft > postRight) {
			return;
		}

		// postorder의 맨 오른쪽이 루트이다.
		int root = post[postRight];
		
		// 루트를 가장 먼저 순회해야 하니 sbPre에 추가
		sbPre.append(root).append(" ");

		// inorder쪽에서도 루트의 위치를 찾는다.
		int inRootIdx = -1;
		for (int i = inLeft; i <= inRight; i++) {
			if (in[i] == root) {
				inRootIdx = i;
				break;
			}
		}

		// 왼쪽 서브트리와 오른쪽 서브트리에 대해서 재귀호출
		int leftSize = inRootIdx - inLeft;
		preorder(inLeft, inRootIdx - 1, postLeft, postLeft + leftSize - 1);
		preorder(inRootIdx + 1, inRight, postLeft + leftSize, postRight - 1);

		return;
	}
}