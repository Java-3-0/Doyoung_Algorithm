// 61880KB, 412ms

package bj15789;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000, MAX_M = 200000;

	static int N, M;

	static class DisjointSet {
		int[] parent = new int[MAX_N + 1];

		public DisjointSet() {
			super();
			Arrays.fill(parent, -1);
		}

		int find(int a) {
			int pa = parent[a];

			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			if (aRoot == bRoot) {
				return false;
			}

			parent[aRoot] += parent[bRoot];
			parent[bRoot] = aRoot;
			return true;
		}

		int getSize(int a) {
			return -parent[find(a)];
		}
	}

	static class Alliance implements Comparable<Alliance> {
		int representative;
		int size;

		public Alliance(int representative, int size) {
			super();
			this.representative = representative;
			this.size = size;
		}

		@Override
		public int compareTo(Alliance a) {
			return -(this.size - a.size);
		}

		@Override
		public String toString() {
			return "Alliance [representative=" + representative + ", size=" + size + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		DisjointSet ds = new DisjointSet();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int X = Integer.parseInt(st.nextToken());
			int Y = Integer.parseInt(st.nextToken());
			ds.union(X, Y);
		}

		// C, H, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int C = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// ctp동맹과 hansol 동맹의 대표자 계산
		int ctp = ds.find(C);
		int hansol = ds.find(H);

		int answer = 0;
		// 시작부터 동맹인 곳 처리
		answer += ds.getSize(ctp);

		boolean[] isVisited = new boolean[MAX_N];

		// 추가 동맹이 될 수 있는 것들
		PriorityQueue<Alliance> pq = new PriorityQueue<>();
		for (int i = 1; i <= N; i++) {
			int rep = ds.find(i);
			if (isVisited[rep]) {
				continue;
			}

			isVisited[rep] = true;
			if (rep != ctp && rep != hansol) {
				pq.offer(new Alliance(rep, ds.getSize(rep)));
			}
		}

		// 인원이 많은 순으로 K개의 동맹 추가 선택
		for (int i = 0; i < K; i++) {
			if (!pq.isEmpty()) {
				answer += pq.poll().size;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

}