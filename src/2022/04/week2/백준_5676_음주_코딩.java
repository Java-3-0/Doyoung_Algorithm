// 105728KB, 600ms

package bj5676;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static final int MAX_K = 100000;

	static int N, K;
	static int treeSize;
	static int[] seq = new int[MAX_N + 1];

	static class SegmentTree {
		int[] products = new int[treeSize];

		/** seq의 내용에 따라 segTree를 생성하는 생성자 */
		public SegmentTree() {
			super();
			init(1, N, 1);
		}

		/** products[]를 재귀적으로 초기화 */
		private int init(int start, int end, int idx) {
			if (start == end) {
				products[idx] = seq[start];
				return products[idx];
			}

			else {
				int mid = (start + end) / 2;
				int prod1 = init(start, mid, 2 * idx);
				int prod2 = init(mid + 1, end, 2 * idx + 1);

				products[idx] = prod1 * prod2;
				return products[idx];
			}
		}

		/** update 연산 수행 */
		private int update(int start, int end, int idx, int targetIdx, int val) {
			if (targetIdx < start || end < targetIdx) {
				return products[idx];
			}

			if (start == end) {
				products[idx] = val;
				return products[idx];
			}

			int mid = (start + end) / 2;
			int prod1 = update(start, mid, 2 * idx, targetIdx, val);
			int prod2 = update(mid + 1, end, 2 * idx + 1, targetIdx, val);
			products[idx] = prod1 * prod2;
			return products[idx];
		}

		/** getProduct 연산 수행 */
		private int getProduct(int start, int end, int idx, int leftRange, int rightRange) {
			if (end < leftRange || rightRange < start) {
				return 1;
			}

			if (leftRange <= start && end <= rightRange) {
				return products[idx];
			}

			int mid = (start + end) / 2;
			int prod1 = getProduct(start, mid, 2 * idx, leftRange, rightRange);
			int prod2 = getProduct(mid + 1, end, 2 * idx + 1, leftRange, rightRange);
			return prod1 * prod2;
		}

		/** 외부에서 편하게 update를 호출하기 위한 오버로딩 함수 */
		public int update(int targetIdx, int val) {
			return update(1, N, 1, targetIdx, val);
		}

		/** 외부에서 편하게 getSum를 호출하기 위한 오버로딩 함수 */
		public int getProduct(int leftRange, int rightRange) {
			return getProduct(1, N, 1, leftRange, rightRange);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		String line = "";
		while ((line = br.readLine()) != null) {
			// N, K 입력
			st = new StringTokenizer(line, " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// 트리 크기 계산
			treeSize = (1 << (int) (1 + Math.ceil(Math.log(N) / Math.log(2))));

			// 수열 초기화
			seq = new int[N + 1];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i <= N; i++) {
				int input = Integer.parseInt(st.nextToken());
				if (input > 0) {
					seq[i] = 1;
				} else if (input == 0) {
					seq[i] = 0;
				} else {
					seq[i] = -1;
				}

			}

			// 구간합을 저장하는 세그먼트 트리 생성
			SegmentTree segmentTree = new SegmentTree();

			// K개의 쿼리 수행
			for (int query = 0; query < K; query++) {
				st = new StringTokenizer(br.readLine(), " ");
				char commandType = st.nextToken().charAt(0);

				if (commandType == 'C') {
					int i = Integer.parseInt(st.nextToken());
					int v = Integer.parseInt(st.nextToken());
					if (v > 0) {
						v = 1;
					} else if (v < 0) {
						v = -1;
					}
					segmentTree.update(i, v);
				}

				else {
					int i = Integer.parseInt(st.nextToken());
					int j = Integer.parseInt(st.nextToken());
					int prod = segmentTree.getProduct(i, j);
					if (prod > 0) {
						sb.append('+');
					} else if (prod == 0) {
						sb.append('0');
					} else {
						sb.append('-');
					}
				}

			} // end for query

			sb.append("\n");

		} // end while

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

}