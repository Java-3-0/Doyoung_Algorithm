// 45304KB, 504ms

package bj1395;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N, M;

	static class SegTree {
		int[] tree;
		boolean[] lazy;

		public SegTree() {
			super();
			int treeSize = (1 << (1 + (int) Math.ceil(Math.log(N) / Math.log(2))));
			tree = new int[treeSize];
			lazy = new boolean[treeSize];
			init(1, N, 1);
		}

		/** 이 노드의 lazy에 저장된 값 처리 */
		public void processLazy(int start, int end, int node) {
			if (lazy[node]) {
				// 자식에게 lazy 값을 넘겨준다
				if (start != end) {
					lazy[leftChildNode(node)] = !lazy[leftChildNode(node)];
					lazy[rightChildNode(node)] = !lazy[rightChildNode(node)];
				}

				// 본인의 lazy 값을 처리한다
				tree[node] = (end - start + 1) - tree[node];

				lazy[node] = false;
			}
			return;
		}

		private int init(int start, int end, int node) {
			if (start == end) {
				return tree[node] = 0;
			}

			int mid = (start + end) / 2;
			int sum1 = init(start, mid, leftChildNode(node));
			int sum2 = init(mid + 1, end, rightChildNode(node));
			return tree[node] = sum1 + sum2;
		}

		/** [leftRange, rightRange] 범위 내의 켜진 스위치들을 카운트한 값을 리턴 */
		private int getCounts(int start, int end, int node, int leftRange, int rightRange) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, node);

			// 범위에 없는 경우, 더해서 자기 자신이 될 수 있게 0을 리턴값으로 한다
			if (start > rightRange || end < leftRange) {
				return 0;
			}

			// start부터 end까지 전체가 [leftRange, rightRange] 범위 내인 경우
			if (leftRange <= start && end <= rightRange) {
				return tree[node];
			}

			// 범위 중 일부만 겹쳐서 재귀호출로 찾아가야 하는 경우
			int mid = (start + end) / 2;
			int sum1 = getCounts(start, mid, leftChildNode(node), leftRange, rightRange);
			int sum2 = getCounts(mid + 1, end, rightChildNode(node), leftRange, rightRange);

			return sum1 + sum2;
		}

		/** leftRange부터 rightRange까지의 스위치 상태를 반전시켜서 켜진 스위치 수를 리턴 */
		private int reverse(int start, int end, int node, int leftRange, int rightRange) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, node);

			// 범위 밖인 경우
			if (rightRange < start || end < leftRange) {
				return tree[node];
			}

			// 범위 안인 경우
			if (leftRange <= start && end <= rightRange) {
				lazy[node] = !lazy[node];
				processLazy(start, end, node);
				return tree[node];
			}

			// 범위가 일부만 겹치는 경우
			int mid = (start + end) / 2;
			int sum1 = reverse(start, mid, leftChildNode(node), leftRange, rightRange);
			int sum2 = reverse(mid + 1, end, rightChildNode(node), leftRange, rightRange);
			tree[node] = sum1 + sum2;
			return tree[node];
		}

		/** getCounts을 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int getCounts(int leftRange, int rightRange) {
			return getCounts(1, N, 1, leftRange, rightRange);
		}

		/** reverse를 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int reverse(int leftRange, int rightRange) {
			return reverse(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기, 쿼리 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// 쿼리 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			int leftRange = Integer.parseInt(st.nextToken());
			int rightRange = Integer.parseInt(st.nextToken());

			if (queryType == 0) {
				segTree.reverse(leftRange, rightRange);
			} else {
				int answer = segTree.getCounts(leftRange, rightRange);
				sb.append(answer).append("\n");
			}

		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 노드의 왼쪽 자식 인덱스를 리턴 */
	public static int leftChildNode(int node) {
		return 2 * node;
	}

	/** 노드의 오른쪽 자식 인덱스를 리턴 */
	public static int rightChildNode(int node) {
		return 2 * node + 1;
	}

}