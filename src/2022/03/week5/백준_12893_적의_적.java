// 212176KB, 556ms

package bj12893;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 2000, MAX_M = 1000000;

	static int N, M;
	static List<Integer>[] adjList;
	static DisjointSet ds;

	static class DisjointSet {
		int[] parent;
		int[] enemy;

		public DisjointSet(int size) {
			super();
			parent = new int[size + 1];
			enemy = new int[size + 1];
			for (int i = 0; i <= size; i++) {
				parent[i] = i;
			}
			Arrays.fill(enemy, -1);
		}

		public int find(int a) {
			if (a < 0) {
				return -1;
			}

			int pa = parent[a];
			if (pa == a) {
				return a;
			}

			return parent[a] = find(pa);
		}

		public boolean processHostile(int A, int B) {
			int enemyA = enemy[A];
			int enemyB = enemy[B];

			int rootA = find(A);
			int rootB = find(B);
			int rootEA = find(enemyA);
			int rootEB = find(enemyB);

			// A와 B가 친구이면 실패
			if (rootA == rootB) {
				return false;
			}

			// A의 적이 A와 친구이면 실패
			if (rootEA == rootA) {
				return false;
			}

			// B의 적이 B와 친구이면 실패
			if (rootEB == rootB) {
				return false;
			}

			// A의 적과 B를 유니온
			if (rootEA >= 0) {
				parent[rootEA] = rootB;
			}

			// B의 적과 A를 유니온
			if (rootEB >= 0) {
				parent[rootEB] = rootA;
			}

			// A와 B를 적으로 등록
			enemy[A] = B;
			enemy[B] = A;

			return true;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// DisjointSet 생성
		ds = new DisjointSet(N);

		boolean success = true;

		// 적대 관계 입력받으면서 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());

			if (!ds.processHostile(A, B)) {
				success = false;
			}
		}

		int answer = success ? 1 : 0;

		System.out.println(answer);

		return;

	} // end main

}