package bj12837;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static final int MAX_Q = 100000;

	static int N, Q;
	static int treeSize;
	static long[] seq = new long[MAX_N + 1];

	static class SegmentTree {
		long[] sums = new long[treeSize];

		/** seq의 내용에 따라 segTree를 생성하는 생성자 */
		public SegmentTree() {
			super();
			init(1, N, 1);
		}

		/** sums[]를 재귀적으로 초기화 */
		private long init(int start, int end, int idx) {
			if (start == end) {
				sums[idx] = seq[start];
				return sums[idx];
			}

			else {
				int mid = (start + end) / 2;
				long sum1 = init(start, mid, 2 * idx);
				long sum2 = init(mid + 1, end, 2 * idx + 1);

				sums[idx] = sum1 + sum2;
				return sums[idx];
			}
		}

		/** update 연산 수행 */
		private long update(int start, int end, int idx, int targetIdx, long val) {
			if (targetIdx < start || end < targetIdx) {
				return sums[idx];
			}

			if (start == end) {
				sums[idx] = val;
				return sums[idx];
			}

			int mid = (start + end) / 2;
			long sum1 = update(start, mid, 2 * idx, targetIdx, val);
			long sum2 = update(mid + 1, end, 2 * idx + 1, targetIdx, val);
			sums[idx] = sum1 + sum2;
			return sums[idx];
		}

		/** getSum 연산 수행 */
		private long getSum(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return 0L;
			}

			if (leftRange <= start && end <= rightRange) {
				return sums[idx];
			}

			int mid = (start + end) / 2;
			long sum1 = getSum(start, mid, 2 * idx, leftRange, rightRange);
			long sum2 = getSum(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
			return sum1 + sum2;
		}

		/** 외부에서 편하게 update를 호출하기 위한 오버로딩 함수 */
		public long update(int targetIdx, long val) {
			return update(1, N, 1, targetIdx, val);
		}

		/** 외부에서 편하게 getSum를 호출하기 위한 오버로딩 함수 */
		public long getSum(int leftRange, int rightRange) {
			return getSum(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, Q 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		// 트리 크기 계산
		treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));

		// 수열 초기화
		seq = new long[N + 1];
		Arrays.fill(seq, 0L);
		
		// 구간합을 저장하는 세그먼트 트리 생성
		SegmentTree segmentTree = new SegmentTree();

		// Q개의 쿼리 수행
		for (int query = 0; query < Q; query++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			
			if (queryType == 1) {
				seq[p] = seq[p] + (long) q;
				segmentTree.update(p, seq[p]);
			}
			else {
				long answer = segmentTree.getSum(p, q);
				sb.append(answer).append("\n");
			}
		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

}