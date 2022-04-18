// 230256KB, 1408ms

package bj12844;

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
		int[] tree;
		int[] lazy;

		public SegTree() {
			super();
			int treeSize = (1 << (1 + (int) Math.ceil(Math.log(N) / Math.log(2))));
			tree = new int[treeSize];
			lazy = new int[treeSize];
			init(0, N - 1, 1);
		}

		/** 이 노드의 lazy에 저장된 값 처리 */
		public void processLazy(int start, int end, int node) {
			if (lazy[node] != 0) {
				// 자식에게 lazy 값을 넘겨준다
				if (start != end) {
					lazy[leftChildIdx(node)] ^= lazy[node];
					lazy[rightChildIdx(node)] ^= lazy[node];
				}

				// 본인의 lazy 값을 처리한다
				if ((end - start + 1) % 2 != 0) {
					tree[node] ^= lazy[node];
				}

				lazy[node] = 0;
			}
			return;
		}

		/** 초기화 */
		private int init(int start, int end, int node) {
			if (start == end) {
				tree[node] = seq[start];
				return tree[node];
			}

			int mid = (start + end) / 2;
			int xor1 = init(start, mid, leftChildIdx(node));
			int xor2 = init(mid + 1, end, rightChildIdx(node));

			return tree[node] = xor1 ^ xor2;

		}

		/** [leftRange, rightRange] 범위 내의 원소들을 모두 XOR한 값을 리턴 */
		private int getXOR(int start, int end, int node, int leftRange, int rightRange) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, node);

			// 범위에 없는 경우, XOR하면 자기 자신이 될 수 있게 0을 리턴값으로 한다
			if (start > rightRange || end < leftRange) {
				return 0;
			}

			// start부터 end까지 전체가 [leftRange, rightRange] 범위 내인 경우
			if (leftRange <= start && end <= rightRange) {
				return tree[node];
			}

			// 범위 중 일부만 겹쳐서 재귀호출로 찾아가야 하는 경우
			int mid = (start + end) / 2;
			int xor1 = getXOR(start, mid, leftChildIdx(node), leftRange, rightRange);
			int xor2 = getXOR(mid + 1, end, rightChildIdx(node), leftRange, rightRange);

			return xor1 ^ xor2;
		}

		/** leftRange부터 rightRange까지의 수를 xor 연산해서 업데이트 */
		private int update(int start, int end, int node, int leftRange, int rightRange, int xor) {
			// 이 노드의 lazy에 저장된 값부터 처리
			processLazy(start, end, node);

			// 범위 밖인 경우
			if (rightRange < start || end < leftRange) {
				return tree[node];
			}

			// 범위 안인 경우
			if (leftRange <= start && end <= rightRange) {
				lazy[node] ^= xor;
				processLazy(start, end, node);
				return tree[node];
			}

			// 범위가 일부만 겹치는 경우
			int mid = (start + end) / 2;
			int xor1 = update(start, mid, leftChildIdx(node), leftRange, rightRange, xor);
			int xor2 = update(mid + 1, end, rightChildIdx(node), leftRange, rightRange, xor);
			tree[node] = xor1 ^ xor2;
			return tree[node];
		}

		/** get을 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int getXOR(int leftRange, int rightRange) {
			return getXOR(0, N - 1, 1, leftRange, rightRange);
		}

		/** update를 외부에서 편하게 호출하기 위한 오버로딩 함수 */
		public int update(int leftRange, int rightRange, int xor) {
			return update(0, N - 1, 1, leftRange, rightRange, xor);
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
				int leftRange = Integer.parseInt(st.nextToken());
				int rightRange = Integer.parseInt(st.nextToken());
				int answer = segTree.getXOR(leftRange, rightRange);
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