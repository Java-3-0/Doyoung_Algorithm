// 114672KB, 2156ms

package bj1517;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500000;
	static final int TREESIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_N) / Math.log(2))));

	static int N;
	static Element[] elements;

	static class SegTree {
		int[] tree = new int[TREESIZE];

		public SegTree() {
			super();
		}

		/** [start, end] 범위 내에 target이 존재하면 tree[node] 값을 1 증가시키고, 재귀 호출 */
		public void update(int start, int end, int node, int target) {
			if (target < start || end < target) {
				return;
			}

			tree[node]++;
			if (start == end) {
				return;
			}

			int mid = (start + end) / 2;
			update(start, mid, node * 2, target);
			update(mid + 1, end, node * 2 + 1, target);
		}

		/** seq[leftRange]부터 seq[rightRange]까지의 합을 구한다 */
		public int getSum(int start, int end, int node, int leftRange, int rightRange) {
			if (leftRange > rightRange) {
				return 0;
			}

			if (end < leftRange || rightRange < start) {
				return 0;
			}

			if (leftRange <= start && end <= rightRange) {
				return tree[node];
			}

			int mid = (start + end) / 2;
			return getSum(start, mid, 2 * node, leftRange, rightRange)
					+ getSum(mid + 1, end, 2 * node + 1, leftRange, rightRange);
		}

		public int getSum(int leftRange, int rightRange) {
			return getSum(0, N - 1, 1, leftRange, rightRange);
		}

		public void update(int target) {
			update(0, N - 1, 1, target);
		}
	}

	static class Element implements Comparable<Element> {
		int idx;
		int number;

		public Element(int idx, int number) {
			super();
			this.idx = idx;
			this.number = number;
		}

		@Override
		public int compareTo(Element e) {
			if (this.number == e.number) {
				return this.idx - e.idx;
			}

			return this.number - e.number;
		}

		@Override
		public String toString() {
			return "Element [idx=" + idx + ", number=" + number + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		elements = new Element[N];
		for (int idx = 0; idx < N; idx++) {
			int number = Integer.parseInt(st.nextToken());
			elements[idx] = new Element(idx, number);
		}

		// 수열을 오름차순으로 정렬
		Arrays.sort(elements);

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// 작은 수부터 처리하면서 자신보다 오른쪽에 있는 더 작은 수의 개수를 센다
		long answer = 0;
		for (Element e : elements) {
			int idx = e.idx;

			int cnt = segTree.getSum(idx + 1, N - 1);

			answer += (long) cnt;

			segTree.update(idx);
		}

		// 결과 출력
		System.out.println(answer);

	} // end main

}