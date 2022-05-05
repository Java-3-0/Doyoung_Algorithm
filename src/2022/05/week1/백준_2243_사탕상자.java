// 51284KB, 416ms

package bj2243;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = 1000000;
	static final int MAX_Q = 100000;
	static final int TREESIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_NUM) / Math.log(2))));

	static int Q;

	static class SegTree {
		/** 각 노드마다 구간의 부분합을 저장 */
		int[] tree = new int[TREESIZE];

		public SegTree() {
			super();
		}

		/** [start, end] 범위 내에 taste이 존재하면 tree[node] 값을 cnt 만큼 증가시키고, 재귀 호출 */
		public void insert(int start, int end, int node, int taste, int cnt) {
			if (taste < start || end < taste) {
				return;
			}

			tree[node] += cnt;
			if (start == end) {
				return;
			}

			int mid = (start + end) / 2;
			insert(start, mid, node * 2, taste, cnt);
			insert(mid + 1, end, node * 2 + 1, taste, cnt);
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

		public void insert(int taste, int cnt) {
			insert(1, MAX_NUM, 1, taste, cnt);
		}

		public int poll(int K) {
			return poll(1, MAX_NUM, 1, K);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 쿼리 수 입력
		Q = Integer.parseInt(br.readLine());

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// Q개의 쿼리 수행
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			// poll 쿼리
			if (queryType == 1) {
				int kth = Integer.parseInt(st.nextToken());
				int answer = segTree.poll(kth);
				sb.append(answer).append("\n");
			}
			
			// insert 쿼리
			else {
				int taste = Integer.parseInt(st.nextToken());
				int cnt = Integer.parseInt(st.nextToken());
				segTree.insert(taste, cnt);
			}
		}
		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}