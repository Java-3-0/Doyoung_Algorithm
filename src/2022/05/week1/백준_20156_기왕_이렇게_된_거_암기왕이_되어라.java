// 98188KB, 1348ms

package bj20156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int MAX_M = 100000;
	static final int MAX_K = 100000;
	static final int NO_MENTOR = -1;

	static int N, M, K;
	static int[] mentor = new int[MAX_N + 1];
	static int[] X = new int[MAX_M + 1];

	static class DisjointSet {
		int[] representative = new int[MAX_N + 1];

		/** 모든 원소의 대표자를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			Arrays.fill(representative, -1);
		}

		/** a의 대표자를 찾는다 */
		public int find(int a) {
			// 자기 자신이 대표자인 경우 그대로 리턴한다.
			if (representative[a] == -1) {
				return a;
			}

			// path compression을 적용하면서 재귀적으로 대표자를 찾는다.
			return representative[a] = find(representative[a]);
		}

		/** a와 b를 같은 집합으로 합친다. 합치는 데 성공하면 true, 실패하면 false를 리턴 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			// 이미 같은 집합인 경우 false를 리턴하고 끝낸다.
			if (aRoot == bRoot) {
				return false;
			}

			// 이외의 경우, 대표자를 갱신하고 true를 리턴한다.
			representative[bRoot] = aRoot;
			return true;
		}
	}

	static class Query implements Comparable<Query> {
		int idx;
		int roundNum;
		int studentA;
		int studentB;

		public Query(int idx, int roundNum, int studentA, int studentB) {
			super();
			this.idx = idx;
			this.roundNum = roundNum;
			this.studentA = studentA;
			this.studentB = studentB;
		}

		// 나중 라운드에 대한 쿼리를 먼저 처리하기 위해서 라운드 번호 내림차순으로 정렬하는 비교함수
		@Override
		public int compareTo(Query q) {
			return -(this.roundNum - q.roundNum);
		}

		@Override
		public String toString() {
			return "Query [idx=" + idx + ", roundNum=" + roundNum + ", studentA=" + studentA + ", studentB=" + studentB
					+ "]";
		}

	}

	static class Result implements Comparable<Result> {
		int idx;
		String str;

		public Result(int idx, String str) {
			super();
			this.idx = idx;
			this.str = str;
		}

		@Override
		public String toString() {
			return "Result [idx=" + idx + ", str=" + str + "]";
		}

		@Override
		public int compareTo(Result r) {
			return this.idx - r.idx;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 쿼리 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 각 학생의 멘토 번호 입력
		st = new StringTokenizer(br.readLine(), " ");
		Arrays.fill(mentor, NO_MENTOR);
		for (int student = 1; student <= N; student++) {
			mentor[student] = Integer.parseInt(st.nextToken());
		}

		// 라운드마다 학생 번호 X를 입력
		Map<Integer, Integer> separateCnt = new HashMap<>();
		for (int round = 1; round <= M; round++) {
			int input = Integer.parseInt(br.readLine());
			X[round] = input;

			separateCnt.put(input, 1 + separateCnt.getOrDefault(input, 0));
		}

		// 쿼리 입력
		PriorityQueue<Query> queries = new PriorityQueue<>();
		for (int idx = 0; idx < K; idx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int roundNum = Integer.parseInt(st.nextToken());
			int studentA = Integer.parseInt(st.nextToken());
			int studentB = Integer.parseInt(st.nextToken());
			queries.offer(new Query(idx, roundNum, studentA, studentB));
		}

		// 마지막 라운드가 끝난 상태로 DisjointSet을 만든다
		DisjointSet ds = new DisjointSet();
		for (int student = 1; student <= N; student++) {
			if (!separateCnt.containsKey(student)) {
				if (mentor[student] != NO_MENTOR) {
					ds.union(student, mentor[student]);
				}
			}
		}

		// 쿼리 처리
		int curRound = M;
		PriorityQueue<Result> results = new PriorityQueue<>();
		while (!queries.isEmpty()) {
			Query q = queries.poll();
			while (q.roundNum < curRound) {
				int student = X[curRound];
				if (separateCnt.containsKey(student)) {
					if (separateCnt.get(student) == 1) {
						separateCnt.remove(student);
					} else {
						separateCnt.put(student, separateCnt.get(student) - 1);
					}
				}

				if (!separateCnt.containsKey(student) && mentor[student] != NO_MENTOR) {
					ds.union(student, mentor[student]);
				}

				curRound--;
			}

			if (ds.find(q.studentA) == ds.find(q.studentB)) {
				results.offer(new Result(q.idx, "Same Same;"));
			} else {
				results.offer(new Result(q.idx, "No;"));
			}
		}

		// 출력
		while (!results.isEmpty()) {
			sb.append(results.poll().str).append("\n");
		}

		System.out.print(sb.toString());
	}
}