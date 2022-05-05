package bj16978;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000, MAX_Q = 100000;
	static final int TREESIZE = (1 << (int) (1 + Math.ceil(Math.log(MAX_N) / Math.log(2))));

	static int N, Q;
	static long[] seq = new long[MAX_N + 1];

	/** 1번 타입의 쿼리 */
	static class Query1 {
		int target;
		long val;

		public Query1(int target, long val) {
			super();
			this.target = target;
			this.val = val;
		}

		@Override
		public String toString() {
			return "Query1 [target=" + target + ", val=" + val + "]";
		}

	}

	/** 2번 타입의 쿼리 */
	static class Query2 implements Comparable<Query2> {
		int idx;
		int k;
		int left;
		int right;

		public Query2(int idx, int k, int left, int right) {
			super();
			this.idx = idx;
			this.k = k;
			this.left = left;
			this.right = right;
		}

		/** k 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Query2 q) {
			return this.k - q.k;
		}
	}

	/** 2번 타입의 쿼리 결과 */
	static class Result implements Comparable<Result> {
		int idx;
		long value;

		public Result(int idx, long value) {
			super();
			this.idx = idx;
			this.value = value;
		}

		/** idx 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Result r) {
			return this.idx - r.idx;
		}

		@Override
		public String toString() {
			return "Result [idx=" + idx + ", value=" + value + "]";
		}

	}

	static class SegTree {
		/** 구간합을 저장하는 노드들이 모인 트리 */
		long[] tree = new long[TREESIZE];

		/** 생성자 */
		public SegTree() {
			super();
			init(1, N, 1);
		}

		/** seq 내용대로 SegTree를 초기화 */
		public void init(int start, int end, int node) {
			if (start == end) {
				tree[node] = seq[start];
				return;
			}

			int mid = (start + end) / 2;
			init(start, mid, 2 * node);
			init(mid + 1, end, 2 * node + 1);

			tree[node] = tree[2 * node] + tree[2 * node + 1];
		}

		/** seq[i] = v로 변경함에 따라 달라지는 구간합들을 모두 갱신한다 */
		public void update(int start, int end, int node, int target, long val) {
			if (target < start || target > end) {
				return;
			}

			if (start == end) {
				tree[node] = val;
				return;
			}

			int mid = (start + end) / 2;
			update(start, mid, 2 * node, target, val);
			update(mid + 1, end, 2 * node + 1, target, val);

			tree[node] = tree[2 * node] + tree[2 * node + 1];
		}

		/** [left, right] 구간의 부분합을 구해서 리턴 */
		public long getSum(int start, int end, int node, int left, int right) {
			if (left > right) {
				return 0L;
			}

			// [start, end]가 [left, right] 구간에 전혀 겹치지 않는 경우
			if (right < start || end < left) {
				return 0L;
			}

			// [start, end]가 [left, right] 구간에 완전히 포함되는 경우
			if (left <= start && end <= right) {
				return tree[node];
			}

			// 일부만 겹치는 경우 재귀 호출
			int mid = (start + end) / 2;
			long sum1 = getSum(start, mid, 2 * node, left, right);
			long sum2 = getSum(mid + 1, end, 2 * node + 1, left, right);
			return sum1 + sum2;
		}

		public void update(int target, long val) {
			update(1, N, 1, target, val);
		}

		public long getSum(int left, int right) {
			return getSum(1, N, 1, left, right);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		// 쿼리 개수 입력
		Q = Integer.parseInt(br.readLine());

		// 1번 타입 쿼리들을 담을 우선순위 큐
		Queue<Query1> queryOnes = new LinkedList<>();
		// 2번 타입 쿼리들을 담을 우선순위 큐
		PriorityQueue<Query2> queryTwos = new PriorityQueue<>();

		// Q개의 쿼리 정보를 입력
		for (int idx = 0; idx < Q; idx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());

			if (queryType == 1) {
				int target = Integer.parseInt(st.nextToken());
				long val = Long.parseLong(st.nextToken());
				queryOnes.offer(new Query1(target, val));
			} else {
				int k = Integer.parseInt(st.nextToken());
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				queryTwos.offer(new Query2(idx, k, left, right));
			}
		}

		// 세그먼트 트리 생성
		SegTree segTree = new SegTree();

		// 적용된 1번 쿼리의 개수를 카운트하는 변수
		int cntApplied = 0;

		// 2번 쿼리들 중 k가 빠른 순서로 실행
		PriorityQueue<Result> results = new PriorityQueue<>();
		while (!queryTwos.isEmpty()) {
			Query2 q2 = queryTwos.poll();

			// k번째 1번 쿼리까지 적용
			while (!queryOnes.isEmpty() && cntApplied < q2.k) {
				Query1 q1 = queryOnes.poll();

				segTree.update(q1.target, q1.val);

				cntApplied++;
			}

			// 2번 쿼리의 결과를 담는다
			long sum = segTree.getSum(q2.left, q2.right);
			results.add(new Result(q2.idx, sum));
		}

		// 담긴 결과들을 idx가 빠른 순서로 출력
		while (!results.isEmpty()) {
			Result res = results.poll();
			sb.append(res.value).append("\n");
		}
		System.out.print(sb.toString());

	} // end main

}