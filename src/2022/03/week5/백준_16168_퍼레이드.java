// 12440KB, 100ms

package bj16168;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 3000, MAX_E = 3000;
	static int V, E;
	static int[] degrees = new int[MAX_V + 1];

	static class DisjointSet {
		/**
		 * i가 루트인 경우 parent[i]에는 음수값이 저장되어 있고 집합의 크기를 나타낸다. i가 루트가 아닌 경우, parent[i]는 루트의
		 * 인덱스를 나타낸다 .
		 */
		int[] parent = new int[MAX_V + 1];

		/** 모든 원소이 자기 자신을 대표자로 가지는 상태로 DisjointSet을 생성하는 생성자 */
		public DisjointSet() {
			super();

			// 자기 자신이 집합의 대표자인 것을 음수로 나타낸다.
			Arrays.fill(parent, -1);
		}

		/** DisjointSet을 초기화한다 */
		public void clear() {
			Arrays.fill(parent, -1);
		}

		/** a가 속해있는 집합의 root를 찾아서 리턴한다 */
		public int find(int a) {
			int pa = parent[a];

			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		/** a가 속해있는 집합과 b가 속해있는 집합을 합친다 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			if (aRoot == bRoot) {
				return false;
			}

			parent[aRoot] += parent[bRoot];
			parent[bRoot] = aRoot;

			return true;
		}

		/** a가 속해 있는 집합의 크기를 리턴 */
		public int getSize(int a) {
			// a의 루트를 찾고
			int aRoot = find(a);

			// 그 크기가 parent[aRoot]에 음수로 저장되어 있는 것을 절대값을 취해서 리턴한다
			return Math.abs(parent[aRoot]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// V, E 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 연결 그래프인지 여부를 확인하기 위해 사용할 분리 집합
		DisjointSet ds = new DisjointSet();

		// 간선 입력
		Arrays.fill(degrees, 0);
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			degrees[from]++;
			degrees[to]++;
			
			ds.union(from, to);
		}

		// 한붓그리기 가능 여부 파악 후 출력
		int cntOdd = 0;
		for (int i = 1; i <= V; i++) {
			if (degrees[i] % 2 != 0) {
				cntOdd++;
			}
		}

		if (ds.getSize(1) == V && cntOdd == 0 || cntOdd == 2) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	} // end main

}