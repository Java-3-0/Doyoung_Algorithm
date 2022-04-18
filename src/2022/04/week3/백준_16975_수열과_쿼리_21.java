// 71928KB, 916ms

package bj16975;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M;
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
		private long get(int start, int end, int idx, int target) {
			// 이 노드의 lazy 값을 처리
			processLazy(start, end, idx);

			// 이 범위가 [leftRange, rightRange]에 전혀 해당되지 아닌 경우
			if (end < target || target < start) {
				return 0L;
			}

			// 이 범위 전체가 [leftRange, rightRange]에 포함되는 경우, 합계를 리턴
			if (start == end) {
				return sums[idx];
			}

			// 이 범위 일부만 [leftRange, rightRange]에 포함되는 경우, 재귀 호출
			int mid = (start + end) / 2;
			return get(start, mid, 2 * idx, target) + get(mid + 1, end, 2 * idx + 1, target);
		}

		/** update를 외부에서 호출하기 위한 오버로딩 함수 */
		public void update(int leftRange, int rightRange, long delta) {
			update(1, N, 1, leftRange, rightRange, delta);
		}

		/** get를 외부에서 호출하기 위한 오버로딩 함수 */
		public long get(int target) {
			return get(1, N, 1, target);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 트리 크기 계산
		treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));

		// 수열 입력
		seq = new long[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		// 쿼리 개수 입력
		M = Integer.parseInt(br.readLine());

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// M개의 쿼리 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());

			if (queryType == 1) {
				int leftRange = Integer.parseInt(st.nextToken());
				int rightRange = Integer.parseInt(st.nextToken());
				long delta = Long.parseLong(st.nextToken());
				segTree.update(leftRange, rightRange, delta);
			} else {
				int target = Integer.parseInt(st.nextToken());
				long answer = segTree.get(target);
				sb.append(answer).append("\n");
			}

		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}