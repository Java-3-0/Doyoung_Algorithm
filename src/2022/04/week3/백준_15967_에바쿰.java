// 161572KB, 608ms

package bj15967;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, Q1, Q2;
	static long[] seq;

	static class SegTree {
		long[] tree;
		long[] lazy;

		public SegTree() {
			super();
			int treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));
			tree = new long[treeSize];
			lazy = new long[treeSize];
			init(1, N, 1);
		}

		public long init(int start, int end, int node) {
			if (start == end) {
				return tree[node] = seq[start];
			}

			else {
				int mid = (start + end) / 2;
				long sum1 = init(start, mid, 2 * node);
				long sum2 = init(mid + 1, end, 2 * node + 1);
				return tree[node] = sum1 + sum2;
			}
			
		}

		private void processLazy(int start, int end, int node) {
			if (lazy[node] != 0L) {
				// 자식에게 lazy 값을 넘겨준다
				if (start != end) {
					lazy[2 * node] += lazy[node];
					lazy[2 * node + 1] += lazy[node];
				}

				// 본인의 lazy 값을 처리한다
				tree[node] += lazy[node] * (end - start + 1);
				lazy[node] = 0L;
			}
		}

		/** update 연산을 처리 */
		private long update(int start, int end, int node, int leftRange, int rightRange, long delta) {
			// 이 노드의 lazy 값을 처리
			processLazy(start, end, node);

			// 이 범위가 [leftRange, rightRange]에 전혀 해당되지 않는 경우
			if (end < leftRange || rightRange < start) {
				return tree[node];
			}

			// 이 범위 전체가 [leftRange, rightRange]에 포함되는 경우, lazy하게 처리
			if (leftRange <= start && end <= rightRange) {
				lazy[node] += delta;
				processLazy(start, end, node);
				return tree[node];
			}

			// 이 범위 중 일부만 [leftRange, rightRange]에 포함되는 경우, 자식 노드에 대해 재귀 호출로 update
			int mid = (start + end) / 2;
			long sum1 = update(start, mid, 2 * node, leftRange, rightRange, delta);
			long sum2 = update(mid + 1, end, 2 * node + 1, leftRange, rightRange, delta);
			return tree[node] = sum1 + sum2;

		}

		/** seq[leftRange]부터 seq[rightRange]까지의 합을 구한다 */
		private long getSum(int start, int end, int node, int leftRange, int rightRange) {
			// 이 노드의 lazy 값을 처리
			processLazy(start, end, node);

			// 이 범위가 [leftRange, rightRange]에 전혀 해당되지 않는 경우
			if (end < leftRange || rightRange < start) {
				return 0L;
			}

			// 이 범위 전체가 [leftRange, rightRange]에 포함되는 경우
			if (leftRange <= start && end <= rightRange) {
				return tree[node];
			}

			// 이 범위 일부만 [leftRange, rightRange]에 포함되는 경우, 재귀 호출
			int mid = (start + end) / 2;
			long sum1 = getSum(start, mid, 2 * node, leftRange, rightRange);
			long sum2 = getSum(mid + 1, end, 2 * node + 1, leftRange, rightRange);
			return sum1 + sum2;
		}

		/** update를 외부에서 호출하기 위한 오버로딩 함수 */
		public void update(int leftRange, int rightRange, long delta) {
			update(1, N, 1, leftRange, rightRange, delta);
		}

		/** getSum을 외부에서 호출하기 위한 오버로딩 함수 */
		public long getSum(int leftRange, int rightRange) {
			return getSum(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		Q1 = Integer.parseInt(st.nextToken());
		Q2 = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new long[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}
		
		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// Q1 + Q2개의 쿼리 수행
		for (int i = 0; i < Q1 + Q2; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			int leftRange = Integer.parseInt(st.nextToken());
			int rightRange = Integer.parseInt(st.nextToken());

			if (queryType == 1) {
				long answer = segTree.getSum(leftRange, rightRange);
				sb.append(answer).append("\n");
			} else {
				long delta = Long.parseLong(st.nextToken());
				segTree.update(leftRange, rightRange, delta);
			}

		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}