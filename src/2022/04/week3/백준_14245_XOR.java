// 195740KB, 1012ms

package bj14245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500000;
	static final int MAX_NUM = 100000;

	static int N, M;
	static int[] seq;

	static class SegTree {
		int[] nodes;
		int[] lazy;

		public SegTree() {
			super();
			int treeSize = (1 << (1 + (int) Math.ceil(Math.log(N) / Math.log(2))));
			nodes = new int[treeSize];
			lazy = new int[treeSize];
			init(0, N - 1, 1);
		}

		/** 이 노드의 lazy에 저장된 값 처리 */
		public void processLazy(int start, int end, int idx) {
			if (lazy[idx] != 0) {
				// 자식에게 lazy 전달
				if (start != end) {
					lazy[leftChildIdx(idx)] ^= lazy[idx];
					lazy[rightChildIdx(idx)] ^= lazy[idx];
				}

				// 자신의 lazy 처리
				nodes[idx] ^= lazy[idx];
				lazy[idx] = 0;
			}
			return;
		}

		/** 초기화 */
		private void init(int start, int end, int idx) {
			if (start == end) {
				nodes[idx] = seq[start];
				return;
			}

			int mid = (start + end) / 2;
			init(start, mid, leftChildIdx(idx));
			init(mid + 1, end, rightChildIdx(idx));
			return;
		}

		/** target번 원소의 값을 리턴 */
		private int get(int start, int end, int idx, int target) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, idx);

			// 찾은 경우
			if (start == end) {
				return nodes[idx];
			}

			// 재귀호출로 찾아가야 하는 경우
			int mid = (start + end) / 2;
			if (target <= mid) {
				return get(start, mid, leftChildIdx(idx), target);
			} else {
				return get(mid + 1, end, rightChildIdx(idx), target);
			}
		}

		/** leftRange부터 rightRange까지의 수를 xor 연산해서 업데이트 */
		private void update(int start, int end, int idx, int leftRange, int rightRange, int xor) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, idx);

			// 범위 밖인 경우
			if (rightRange < start || end < leftRange) {
				return;
			}

			// 범위 안인 경우
			if (leftRange <= start && end <= rightRange) {
				lazy[idx] ^= xor;
				processLazy(start, end, idx);
				return;
			}

			// 범위가 일부만 겹치는 경우
			int mid = (start + end) / 2;
			update(start, mid, leftChildIdx(idx), leftRange, rightRange, xor);
			update(mid + 1, end, rightChildIdx(idx), leftRange, rightRange, xor);
			return;
		}

		/** get을 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int get(int target) {
			return get(0, N - 1, 1, target);
		}

		/** update를 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public void update(int leftRange, int rightRange, int xor) {
			update(0, N - 1, 1, leftRange, rightRange, xor);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// 쿼리 개수 입력
		M = Integer.parseInt(br.readLine());

		// 쿼리 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			if (queryType == 1) {
				int leftRange = Integer.parseInt(st.nextToken());
				int rightRange = Integer.parseInt(st.nextToken());
				int xor = Integer.parseInt(st.nextToken());
				segTree.update(leftRange, rightRange, xor);
			} else {
				int target = Integer.parseInt(st.nextToken());
				int answer = segTree.get(target);
				sb.append(answer).append("\n");
			}

		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 노드의 왼쪽 자식 인덱스를 리턴 */
	public static int leftChildIdx(int idx) {
		return 2 * idx;
	}

	/** 노드의 오른쪽 자식 인덱스를 리턴 */
	public static int rightChildIdx(int idx) {
		return 2 * idx + 1;
	}

}