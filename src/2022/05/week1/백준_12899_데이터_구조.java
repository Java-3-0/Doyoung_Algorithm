// 323332KB, 1432ms

package bj12899;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = 2000000;
	static final int MAX_Q = 2000000;
	static final int TREESIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_NUM) / Math.log(2))));

	static int Q;

	static class SegTree {
		int[] tree = new int[TREESIZE];

		public SegTree() {
			super();
		}

		/** [start, end] 범위 내에 target이 존재하면 tree[node] 값을 1 증가시키고, 재귀 호출 */
		public void insert(int start, int end, int node, int target) {
			if (target < start || end < target) {
				return;
			}

			tree[node]++;
			if (start == end) {
				return;
			}

			int mid = (start + end) / 2;
			insert(start, mid, node * 2, target);
			insert(mid + 1, end, node * 2 + 1, target);
		}

		/** [start, end] 범위 내에 k번째 수가 존재하면 tree[node] 값을 1 감소시키고, 재귀 호출 */
		public int poll(int start, int end, int node, int K) {
			tree[node]--;
			if (start == end) {
				return start;
			}

			int mid = (start + end) / 2;

			// 왼쪽 자식 노드에서 K번째 수 찾기
			if (tree[2 * node] >= K) {
				return poll(start, mid, node * 2, K);
			}

			// 오른쪽 자식 노드에서 (K - 왼쪽 서브트리에 있는 자식 수)번째 노드 찾기
			else {
				K = K - tree[node * 2];
				return poll(mid + 1, end, node * 2 + 1, K);
			}

		}

		public void insert(int target) {
			insert(0, MAX_NUM - 1, 1, target);
		}

		public int poll(int K) {
			return poll(0, MAX_NUM - 1, 1, K);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 쿼리 수 입력 입력
		Q = Integer.parseInt(br.readLine());

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// Q개의 쿼리 수행
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			// insert 쿼리
			if (queryType == 1) {
				int num = Integer.parseInt(st.nextToken());
				segTree.insert(num);
			}
			// poll 쿼리
			else {
				int kth = Integer.parseInt(st.nextToken());
				int answer = segTree.poll(kth);
				sb.append(answer).append("\n");
			}
		}
		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}