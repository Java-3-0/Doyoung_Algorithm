// 109328KB, 500ms

package bj11505;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long MOD = 1000000007L;

	static final int MAX_N = 1000000;

	static int N, M, K;
	static int treeSize;
	static long[] seq = new long[MAX_N + 1];

	static class SegmentTree {
		long[] products = new long[treeSize];

		/** seq의 내용에 따라 segTree를 생성하는 생성자 */
		public SegmentTree() {
			super();
			init(1, N, 1);
		}

		/** products[]를 재귀적으로 초기화 */
		private long init(int start, int end, int idx) {
			if (start == end) {
				products[idx] = seq[start];
				return products[idx];
			}

			else {
				int mid = (start + end) / 2;
				long prod1 = init(start, mid, 2 * idx);
				long prod2 = init(mid + 1, end, 2 * idx + 1);

				products[idx] = modMult(prod1, prod2);
				return products[idx];
			}
		}

		/** update 연산 수행 */
		private long update(int start, int end, int idx, int targetIdx, long val) {
			if (targetIdx < start || end < targetIdx) {
				return products[idx];
			}

			if (start == end) {
				products[idx] = val;
				return products[idx];
			}

			int mid = (start + end) / 2;
			long prod1 = update(start, mid, 2 * idx, targetIdx, val);
			long prod2 = update(mid + 1, end, 2 * idx + 1, targetIdx, val);
			products[idx] = modMult(prod1, prod2);
			return products[idx];
		}

		/** getProduct 연산 수행 */
		private long getProduct(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return 1L;
			}

			if (leftRange <= start && end <= rightRange) {
				return products[idx];
			}

			int mid = (start + end) / 2;
			long prod1 = getProduct(start, mid, 2 * idx, leftRange, rightRange);
			long prod2 = getProduct(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
			return modMult(prod1, prod2);
		}

		/** 외부에서 편하게 update를 호출하기 위한 오버로딩 함수 */
		public long update(int targetIdx, long val) {
			return update(1, N, 1, targetIdx, val);
		}

		/** 외부에서 편하게 getProduct를 호출하기 위한 오버로딩 함수 */
		public long getProduct(int leftRange, int rightRange) {
			return getProduct(1, N, 1, leftRange, rightRange);
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
		
		// 구간곱을 저장하는 세그먼트 트리 생성
		SegmentTree segmentTree = new SegmentTree();
		
		// M, K개의 쿼리 수행
		for (int i = 0; i < M + K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			long c = Long.parseLong(st.nextToken());
			if (a == 1) {
				segmentTree.update(b, c);
			}
			else {
				long answer = segmentTree.getProduct(b, (int)c);
				sb.append(answer).append("\n");
			}
		}
		
		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 모듈러 곱셈 결과값을 리턴하는 함수 */
	static long modMult(long a, long b) {
		return (a * b) % MOD;
	}

}