// 62732KB, 600ms

package bj2357;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int TREE_SIZE = 1 + (int) Math.pow(2, 18);
	static final int INF = (int) (1e9 + 1e6);
	static final int NEG_INF = -INF;

	static int N, M;
	static int[] seq;

	static class SegTree {
		int[] mins = new int[TREE_SIZE];
		int[] maxs = new int[TREE_SIZE];

		/** 생성자 (mins와 maxs를 seq 내용에 맞게 초기화) */
		public SegTree() {
			super();
			initMins(1, N, 1);
			initMaxs(1, N, 1);
		}

		/** mins를 재귀적으로 초기화 */
		private int initMins(int start, int end, int idx) {
			if (start == end) {
				mins[idx] = seq[start];
				return mins[idx];
			}

			else {
				int mid = (start + end) / 2;
				mins[idx] = Math.min(initMins(start, mid, 2 * idx), initMins(mid + 1, end, 2 * idx + 1));
				return mins[idx];
			}
		}

		/** maxs를 재귀적으로 초기화 */
		private int initMaxs(int start, int end, int idx) {
			if (start == end) {
				maxs[idx] = seq[start];
				return maxs[idx];
			}

			else {
				int mid = (start + end) / 2;
				maxs[idx] = Math.max(initMaxs(start, mid, 2 * idx), initMaxs(mid + 1, end, 2 * idx + 1));
				return maxs[idx];
			}
		}

		/** seq[leftRange]부터 seq[rightRange]까지의 최소값을 구한다 */
		private int getMin(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return INF;
			}

			if (leftRange <= start && end <= rightRange) {
				return mins[idx];
			}

			int mid = (start + end) / 2;
			return Math.min(getMin(start, mid, 2 * idx, leftRange, rightRange),
					getMin(mid + 1, end, 2 * idx + 1, leftRange, rightRange));
		}

		/** seq[leftRange]부터 seq[rightRange]까지의 최대값을 구한다 */
		private int getMax(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return NEG_INF;
			}

			if (leftRange <= start && end <= rightRange) {
				return maxs[idx];
			}

			int mid = (start + end) / 2;
			return Math.max(getMax(start, mid, 2 * idx, leftRange, rightRange),
					getMax(mid + 1, end, 2 * idx + 1, leftRange, rightRange));
		}

		/** 외부에서 getMax 편하게 호출하기 위한 오버로딩 함수 */
		public int getMin(int leftRange, int rightRange) {
			return getMin(1, N, 1, leftRange, rightRange);
		}

		/** 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int getMax(int leftRange, int rightRange) {
			return getMax(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// M개의 쿼리 수행
		for (int i = 0; i < M; i++) {
			// a, b 입력
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			sb.append(segTree.getMin(a, b)).append(" ");
			sb.append(segTree.getMax(a, b)).append("\n");
		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}