// 102024KB, 464ms

package bj2042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static long[] seq;

	static class SegTree {
		long[] nodes = new long[1 + (int) Math.pow(2, 21)];

		public SegTree() {
			super();
			init(1, N, 1);
		}

		/** 하위 노드들을 재귀적으로 초기화하고, seq[start]부터 seq[end] 까지의 합을 nodes[idx]에 담는다 */
		private long init(int start, int end, int idx) {
			if (start == end) {
				nodes[idx] = seq[start];
				return nodes[idx];
			}

			else {
				int mid = (start + end) / 2;
				nodes[idx] = init(start, mid, 2 * idx) + init(mid + 1, end, 2 * idx + 1);
				return nodes[idx];
			}
		}

		/** [start, end] 범위 내에 target이 존재하면 nodes[idx] 값을 delta만큼 업데이트하고, 재귀 호출 */
		public void update(int start, int end, int idx, int target, long delta) {
			if (target < start || end < target) {
				return;
			}

			nodes[idx] += delta;
			if (start == end) {
				return;
			}

			int mid = (start + end) / 2;
			update(start, mid, idx * 2, target, delta);
			update(mid + 1, end, idx * 2 + 1, target, delta);
		}

		/** seq[leftRange]부터 seq[rightRange]까지의 합을 구한다 */
		public long getSum(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return 0L;
			}

			if (leftRange <= start && end <= rightRange) {
				return nodes[idx];
			}

			int mid = (start + end) / 2;
			return getSum(start, mid, 2 * idx, leftRange, rightRange)
					+ getSum(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
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
			long c = Long.parseLong(st.nextToken());

			// update 수행
			if (a == 1) {
				long delta = c - seq[b];
				seq[b] = c;
				segTree.update(1, N, 1, b, delta);
			}
			// getSum 수행
			else {
				long answer = segTree.getSum(1, N, 1, b, (int) c);
				sb.append(answer).append("\n");
			}
		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}