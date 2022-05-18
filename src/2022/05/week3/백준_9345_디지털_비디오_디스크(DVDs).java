// 333956KB, 1648ms

package bj9345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;
	static final int TREE_SIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_N) / Math.log(2))));
	static final int INF = 987654321;
	static final MinMax INVALID = new MinMax(INF, -INF); // 다른 값과 min, max 연산을 했을 때 아무 변화가 생기지 않게 하는 의미없는 값

	static int N, K;
	static int[] seq = new int[MAX_N];

	/** 최솟값, 최댓값을 담을 객체 */
	static class MinMax {
		int min;
		int max;

		public MinMax(int min, int max) {
			super();
			this.min = min;
			this.max = max;
		}

		@Override
		public String toString() {
			return "MinMax [min=" + min + ", max=" + max + "]";
		}

	}

	static class SegTree {
		MinMax[] tree = new MinMax[TREE_SIZE];

		public SegTree() {
			super();
		}

		/** 세그트리를 seq[]의 값대로 초기화 */
		private MinMax init(int start, int end, int node) {
			if (start == end) {
				tree[node] = new MinMax(seq[start], seq[start]);
				return tree[node];
			} else {
				int mid = (start + end) / 2;
				MinMax leftChild = init(start, mid, 2 * node);
				MinMax rightChild = init(mid + 1, end, 2 * node + 1);

				tree[node] = calcMinMax(leftChild, rightChild);
				return tree[node];
			}

		}

		/** [A, B] 구간의 최솟값, 최댓값 구하기 */
		private MinMax get(int start, int end, int node, int A, int B) {
			// [start, end]가 [A, B] 구간에 전혀 겹치지 않는 경우
			if (B < start || end < A) {
				return INVALID;
			}

			// [start, end]가 [A, B] 구간에 완전히 포함되는 경우
			if (A <= start && end <= B) {
				return tree[node];
			}

			// 일부만 겹치는 경우 재귀 호출
			int mid = (start + end) / 2;
			MinMax leftChild = get(start, mid, 2 * node, A, B);
			MinMax rightChild = get(mid + 1, end, 2 * node + 1, A, B);

			return calcMinMax(leftChild, rightChild);
		}

		/** target번 원소를 value로 업데이트함에 따라 세그트리 노드들을 갱신 */
		private MinMax update(int start, int end, int node, int target, int value) {
			if (target < start || end < target) {
				return tree[node];
			}

			tree[node] = calcMinMax(tree[node], new MinMax(value, value));

			if (start == end) {
				tree[node].min = value;
				tree[node].max = value;
				return tree[node];
			}

			int mid = (start + end) / 2;
			MinMax leftChild = update(start, mid, 2 * node, target, value);
			MinMax rightChild = update(mid + 1, end, 2 * node + 1, target, value);
			tree[node] = calcMinMax(leftChild, rightChild);

			return tree[node];
		}

		/** 세그트리를 seq[]의 값대로 초기화 */
		public void init() {
			init(0, MAX_N - 1, 1);
		}

		/** [A, B] 구간의 최솟값, 최댓값 구하기 */
		public MinMax get(int A, int B) {
			return get(0, MAX_N - 1, 1, A, B);
		}

		/** target번 원소를 value로 업데이트함에 따라 세그트리 노드들을 갱신 */
		public MinMax update(int target, int value) {
			return update(0, MAX_N - 1, 1, target, value);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// 수열 초기화
			for (int i = 0; i < MAX_N; i++) {
				seq[i] = i;
			}

			// 세그먼트 트리 초기화
			segTree.init();

			// N, K 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// 쿼리 수행
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int queryType = Integer.parseInt(st.nextToken());
				int A = Integer.parseInt(st.nextToken());
				int B = Integer.parseInt(st.nextToken());

				// 바꿔 끼우기
				if (queryType == 0) {
					int tmp = seq[A];
					seq[A] = seq[B];
					seq[B] = tmp;

					segTree.update(A, seq[A]);
					segTree.update(B, seq[B]);
				}

				// 가져오기
				else {
					MinMax result = segTree.get(A, B);
					if (result.min == A && result.max == B) {
						sb.append("YES").append("\n");
					} else {
						sb.append("NO").append("\n");
					}
				}
			}

		} // end for tc

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

	/** m1과 m2의 최솟값, 최댓값을 리턴 */
	public static MinMax calcMinMax(MinMax m1, MinMax m2) {
		int min = Math.min(m1.min, m2.min);
		int max = Math.max(m1.max, m2.max);

		return new MinMax(min, max);
	}

}