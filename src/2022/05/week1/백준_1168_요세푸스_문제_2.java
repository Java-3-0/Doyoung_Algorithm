// 20756KB, 228ms

package bj1168;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int MAX_K = 100000;
	static final int TREESIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_N) / Math.log(2))));

	static int N, K;

	static class SegTree {
		int[] tree = new int[TREESIZE];

		public SegTree() {
			super();
			init(1, N, 1);
		}

		/** [start, end] 범위의 모든 리프 노드가 1의 값을 가지고, 상위 노드들은 하위 노드들의 합을 가지도록 초기화 */
		int init(int start, int end, int node) {
			if (start == end) {
				return tree[node] = 1;
			}

			int mid = (start + end) / 2;
			return tree[node] = init(start, mid, 2 * node) + init(mid + 1, end, 2 * node + 1);
		}

		/** [start, end] 범위 내에 k번째 수가 존재하면 tree[node] 값을 1 감소시키고, 재귀 호출 */
		public int poll(int start, int end, int node, int K) {
			tree[node]--;
			if (start == end) {
				return start;
			}

			int mid = (start + end) / 2;

			// 왼쪽 자식 노드에서 K번째 수 찾기
			if (tree[2 * node] >= K) {
				return poll(start, mid, node * 2, K);
			}

			// 오른쪽 자식 노드에서 (K - 왼쪽 서브트리에 있는 자식 수)번째 노드 찾기
			else {
				K = K - tree[node * 2];
				return poll(mid + 1, end, node * 2 + 1, K);
			}

		}

		/** 외부에서 poll을 편하게 호출하기 위한 오버로딩 함수 */
		public int poll(int K) {
			return poll(1, N, 1, K);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// 모두 제거될 때까지 반복
		int cur = K;
		sb.append("<");
		for (int i = 1; i <= N; i++) {
			// cur번째 제거
			int answer = segTree.poll(cur);
			sb.append(answer);

			if (i == N) {
				break;
			} else {
				sb.append(", ");

				// 다음 cur 위치 계산해서 cur를 갱신
				int remaining = N - i;
				int next = (cur + (K - 1)) % remaining;
				next = next == 0 ? remaining : next;
				cur = next;
			}
		}
		sb.append(">\n");

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}