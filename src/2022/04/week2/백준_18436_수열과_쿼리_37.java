// 72880KB, 688ms

package bj18436;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static final int MAX_Q = 100000;

	static int N, Q;
	static int treeSize;
	static int[] seq = new int[MAX_N + 1];

	static class SegmentTree {
		int[] countEvens = new int[treeSize];

		/** seq의 내용에 따라 segTree를 생성하는 생성자 */
		public SegmentTree() {
			super();
			init(1, N, 1);
		}

		/** sums[]를 재귀적으로 초기화 */
		private int init(int start, int end, int idx) {
			if (start == end) {
				countEvens[idx] = (seq[start] % 2 == 0) ? 1 : 0;
				return countEvens[idx];
			}

			else {
				int mid = (start + end) / 2;
				int cnt1 = init(start, mid, 2 * idx);
				int cnt2 = init(mid + 1, end, 2 * idx + 1);

				countEvens[idx] = cnt1 + cnt2;
				return countEvens[idx];
			}
		}

		/** update 연산 수행 */
		private int update(int start, int end, int idx, int targetIdx, int val) {
			if (targetIdx < start || end < targetIdx) {
				return countEvens[idx];
			}

			if (start == end) {
				countEvens[idx] = (val % 2 == 0) ? 1 : 0;
				return countEvens[idx];
			}

			int mid = (start + end) / 2;
			int cnt1 = update(start, mid, 2 * idx, targetIdx, val);
			int cnt2 = update(mid + 1, end, 2 * idx + 1, targetIdx, val);
			countEvens[idx] = cnt1 + cnt2;
			return countEvens[idx];
		}

		/** get 연산 수행 */
		private int get(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return 0;
			}

			if (leftRange <= start && end <= rightRange) {
				return countEvens[idx];
			}

			int mid = (start + end) / 2;
			int cnt1 = get(start, mid, 2 * idx, leftRange, rightRange);
			int cnt2 = get(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
			return cnt1 + cnt2;
		}

		/** 외부에서 편하게 update를 호출하기 위한 오버로딩 함수 */
		public int update(int targetIdx, int val) {
			return update(1, N, 1, targetIdx, val);
		}

		/** 외부에서 편하게 get를 호출하기 위한 오버로딩 함수 */
		public int get(int leftRange, int rightRange) {
			return get(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 트리 크기 계산
		treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));

		// 수열 초기화
		seq = new int[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// Q 입력
		Q = Integer.parseInt(br.readLine());

		// 구간합을 저장하는 세그먼트 트리 생성
		SegmentTree segmentTree = new SegmentTree();

		// Q개의 쿼리 수행
		for (int query = 0; query < Q; query++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());

			if (queryType == 1) {
				int i = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				segmentTree.update(i, x);
			} else if (queryType == 2) {
				int l = Integer.parseInt(st.nextToken());
				int r = Integer.parseInt(st.nextToken());
				int answer = segmentTree.get(l, r);
				sb.append(answer).append("\n");
			} else {
				int l = Integer.parseInt(st.nextToken());
				int r = Integer.parseInt(st.nextToken());
				int answer = (r - l + 1) - segmentTree.get(l, r);
				sb.append(answer).append("\n");
			}
		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

}