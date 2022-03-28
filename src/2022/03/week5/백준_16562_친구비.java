// 18588KB, 180ms

package bj16562;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 10000, MAX_M = 10000, MAX_K = 10000000;

	static int N, M, K;

	static class DisjointSet {
		int[] parent;

		public DisjointSet(int[] A) {
			super();
			this.parent = new int[A.length];
			for (int i = 0; i < A.length; i++) {
				this.parent[i] = -A[i];
			}
		}

		/** a가 속해있는 집합의 root를 찾아서 리턴한다 */
		public int find(int a) {
			int pa = parent[a];

			// 자기 자신이 대표자인 경우, 그대로 리턴
			if (pa < 0) {
				return a;
			}

			// 이외의 경우, path compression 적용하면서 대표자 갱신
			return parent[a] = find(pa);
		}

		/** a가 속해있는 집합과 b가 속해있는 집합을 합친다 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			// 둘이 이미 같은 집합이라면
			if (aRoot == bRoot) {
				return false;
			}

			// 집합의 최소 친구비 갱신 (음수니까 max해야 절대값이 작다)
			parent[aRoot] = Math.max(parent[aRoot], parent[bRoot]);
			// 합치기
			parent[bRoot] = aRoot;

			return true;
		}

		public int getMoney(int a) {
			return -parent[find(a)];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// A[] 입력
		int[] A = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		// 상호 배타적 집합 생성
		DisjointSet ds = new DisjointSet(A);

		// 친구 관계 입력받아서 union
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int v = Integer.parseInt(st.nextToken()) - 1;
			int w = Integer.parseInt(st.nextToken()) - 1;
			ds.union(v, w);
		}

		// 집합의 대표자들만 set에 넣는다.
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < N; i++) {
			set.add(ds.find(i));
		}

		// 그들의 비용 합을 계산
		int minMoney = 0;
		for (int idx : set) {
			minMoney += ds.getMoney(idx);
		}

		// 정답 출력
		if (minMoney <= K) {
			System.out.println(minMoney);
		} else {
			System.out.println("Oh no");
		}

	} // end main

}