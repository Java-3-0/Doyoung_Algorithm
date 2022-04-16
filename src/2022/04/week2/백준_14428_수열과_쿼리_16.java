// 80280KB, 696ms

package bj14428;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static final int MAX_M = 100000;
	static final int INF = (int) (1e9 + 1e6);

	static int N, M;
	static int treeSize;
	static int[] seq = new int[MAX_N + 1];

	static class Status {
		int idx;
		int val;

		public Status(int idx, int val) {
			super();
			this.idx = idx;
			this.val = val;
		}

	}

	static class SegmentTree {
		Status[] mins = new Status[treeSize];

		/** seq의 내용에 따라 segTree를 생성하는 생성자 */
		public SegmentTree() {
			super();
			init(1, N, 1);
		}

		/** mins[]를 재귀적으로 초기화 */
		private Status init(int start, int end, int idx) {
			if (start == end) {
				mins[idx] = new Status(start, seq[start]);
				return mins[idx];
			}

			else {
				int mid = (start + end) / 2;
				Status min1 = init(start, mid, 2 * idx);
				Status min2 = init(mid + 1, end, 2 * idx + 1);

				if (min1.val <= min2.val) {
					mins[idx] = min1;
				}
				else {
					mins[idx] = min2;
				}
				
				return mins[idx];
			}
		}

		/** update 연산 수행 */
		private Status update(int start, int end, int idx, int targetIdx, int val) {
			if (targetIdx < start || end < targetIdx) {
				return mins[idx];
			}

			if (start == end) {
				mins[idx].val = val;
				return mins[idx];
			}

			int mid = (start + end) / 2;
			Status min1 = update(start, mid, 2 * idx, targetIdx, val);
			Status min2 = update(mid + 1, end, 2 * idx + 1, targetIdx, val);
			if (min1.val <= min2.val) {
				mins[idx] = min1;
			}
			else {
				mins[idx] = min2;
			}
			
			return mins[idx];
		}

		/** getMin 연산 수행 */
		private Status getMin(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return new Status(INF, INF);
			}

			if (leftRange <= start && end <= rightRange) {
				return mins[idx];
			}

			int mid = (start + end) / 2;
			Status r1 = getMin(start, mid, 2 * idx, leftRange, rightRange);
			Status r2 = getMin(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
			if (r1.val <= r2.val) {
				return r1;
			} else {
				return r2;
			}
		}

		/** 외부에서 편하게 update를 호출하기 위한 오버로딩 함수 */
		public Status update(int targetIdx, int val) {
			return update(1, N, 1, targetIdx, val);
		}

		/** 외부에서 편하게 getMin를 호출하기 위한 오버로딩 함수 */
		public Status getMin(int leftRange, int rightRange) {
			return getMin(1, N, 1, leftRange, rightRange);
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

		// 수열 입력
		seq = new int[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// M 입력
		M = Integer.parseInt(br.readLine());

		// 구간합을 저장하는 세그먼트 트리 생성
		SegmentTree segmentTree = new SegmentTree();

		// M 개의 쿼리 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			// getMin 쿼리 수행
			if (a == 2) {
				Status result = segmentTree.getMin(b, c);
				sb.append(result.idx).append("\n");
			}
			// update 쿼리 수행
			else {
				segmentTree.update(b, c);
			}

		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

}