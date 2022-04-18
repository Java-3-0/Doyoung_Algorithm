// 125756KB, 688ms

package bj10999;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static long[] seq;
	static int treeSize;

	static class SegTree {
		long[] sums = new long[treeSize];
		long[] lazy = new long[treeSize];

		public SegTree() {
			super();
			init(1, N, 1);
		}

		/** 하위 노드들을 재귀적으로 초기화하고, seq[start]부터 seq[end] 까지의 합을 nodes[idx]에 담는다 */
		private long init(int start, int end, int idx) {
			if (start == end) {
				sums[idx] = seq[start];
				return sums[idx];
			}

			else {
				int mid = (start + end) / 2;
				sums[idx] = init(start, mid, 2 * idx) + init(mid + 1, end, 2 * idx + 1);
				return sums[idx];
			}
		}

		private void processLazy(int start, int end, int idx) {
			if (lazy[idx] != 0L) {
				// 자식에게 lazy 값을 넘겨준다
				if (start != end) {
					lazy[2 * idx] += lazy[idx];
					lazy[2 * idx + 1] += lazy[idx];
				}

				// 본인의 lazy 값을 처리한다
				sums[idx] += lazy[idx] * (end - start + 1);
				lazy[idx] = 0L;
			}
		}

		/** update 연산을 처리 */
		private void update(int start, int end, int idx, int leftRange, int rightRange, long delta) {
			// 이 노드의 lazy 값을 처리
			processLazy(start, end, idx);

			// 이 범위가 [leftRange, rightRange]에 전혀 해당되지 아닌 경우
			if (end < leftRange || rightRange < start) {
				return;
			}

			// 이 범위 전체가 [leftRange, rightRange]에 포함되는 경우, lazy하게 처리
			if (leftRange <= start && end <= rightRange) {
				lazy[idx] = delta;
				processLazy(start, end, idx);

				return;
			}

			// 이 범위 중 일부만 [leftRange, rightRange]에 포함되는 경우, 자식 노드에 대해 재귀 호출로 update
			else {
				int mid = (start + end) / 2;
				update(start, mid, 2 * idx, leftRange, rightRange, delta);
				update(mid + 1, end, 2 * idx + 1, leftRange, rightRange, delta);
				sums[idx] = sums[2 * idx] + sums[2 * idx + 1];
			}

		}

		/** seq[leftRange]부터 seq[rightRange]까지의 합을 구한다 */
		private long getSum(int start, int end, int idx, int leftRange, int rightRange) {
			// 이 노드의 lazy 값을 처리
			processLazy(start, end, idx);

			// 이 범위가 [leftRange, rightRange]에 전혀 해당되지 아닌 경우
			if (end < leftRange || rightRange < start) {
				return 0L;
			}

			// 이 범위 전체가 [leftRange, rightRange]에 포함되는 경우, 합계를 리턴
			if (leftRange <= start && end <= rightRange) {
				return sums[idx];
			}

			// 이 범위 일부만 [leftRange, rightRange]에 포함되는 경우, 재귀 호출
			int mid = (start + end) / 2;
			return getSum(start, mid, 2 * idx, leftRange, rightRange)
					+ getSum(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
		}

		/** update를 외부에서 호출하기 위한 오버로딩 함수 */
		public void update(int leftRange, int rightRange, long delta) {
			update(1, N, 1, leftRange, rightRange, delta);
		}

		/** getSum를 외부에서 호출하기 위한 오버로딩 함수 */
		public long getSum(int leftRange, int rightRange) {
			return getSum(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 트리 크기 계산
		treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));

		// 수열 입력
		seq = new long[N + 1];
		for (int i = 1; i <= N; i++) {
			seq[i] = Long.parseLong(br.readLine());
		}

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// M + K개의 쿼리 수행
		for (int i = 0; i < M + K; i++) {
			// a, b, c 입력
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			if (a == 1) {
				long d = Long.parseLong(st.nextToken());
				segTree.update(b, c, d);
			} else {
				long answer = segTree.getSum(b, c);
				sb.append(answer).append("\n");
			}

		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}