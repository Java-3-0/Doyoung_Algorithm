// 114692KB, 460ms

package bj11724;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_M = MAX_V * (MAX_V - 1) / 2;

	static class DisjointSet {
		/**
		 * i가 root인 경우 parent[i]에는 음수값이 저장되어 있고 집합의 크기를 나타낸다. i가 root가 아닌 경우, parent[i]는
		 * root의 인덱스를 나타낸다 .
		 */
		int[] parent;

		/** 집합 원소 개수를 받아서 DisjointSet 객체를 만드는 생성자 */
		public DisjointSet(int size) {
			super();

			// 1부터 V까지의 인덱스를 사용할 것이므로, 사이즈를 하나 크게 준다.
			parent = new int[size + 1];

			// 자기 자신이 root인 것을 음수로 나타내고, 그 집합에 있는 원소 수는 그 음수값의 크기로 나타낸다.
			// 처음엔 모두 자기 자신 하나만을 가지는 집합에 속한 상태로 시작하므로 -1로 초기화한다.
			Arrays.fill(parent, -1);
		}

		/**
		 * 원소가 속해 있는 집합의 root를 리턴한다.
		 * 
		 * @param a : root를 찾을 원소
		 * @return a가 속한 집합의 root
		 */
		public int find(int a) {
			int pa = parent[a];

			// 자기 자신이 root인 경우, 그대로 리턴
			if (pa < 0) {
				return a;
			}

			// 이외의 경우, path compression 적용하면서 root 갱신
			return parent[a] = find(pa);
		}

		/**
		 * 두 원소가 속해있는 집합을 합친다.
		 * 
		 * @param a : 합칠 원소1의 인덱스
		 * @param b : 합칠 원소2의 인덱스
		 * @return 합칠 수 있다면 true 리턴, 이미 같은 집합에 있어서 합칠 수 없다면 false 리턴
		 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			// 둘이 이미 같은 집합이라면 실패
			if (aRoot == bRoot) {
				return false;
			}

			// a 트리 아래에 b 트리를 붙이는 식으로 합친다.

			// 집합 크기 갱신
			parent[aRoot] += parent[bRoot];
			// 합치기
			parent[bRoot] = aRoot;

			return true;
		}

		/** a가 속해 있는 집합의 크기를 리턴 */
		public int getSize(int a) {
			// a의 root를 찾는다.
			int aRoot = find(a);

			// 집합의 크기는 parent[aRoot]에 음수로 저장되어 있으니 절대값을 취해서 리턴한다
			return Math.abs(parent[aRoot]);
		}

		/** DisjointSet 내에 존재하는 집합의 개수를 리턴 */
		public int getNumberOfSets() {
			int ret = 0;

			// parent가 음수인 것만 세면, root의 개수를 센 것이다. root의 개수가 곧 그룹의 개수이다.
			for (int i = 1; i < parent.length; i++) {
				if (parent[i] < 0) {
					ret++;
				}
			}

			return ret;
		}
	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		DisjointSet ds = new DisjointSet(V);
		
		// 주어지는 간선마다 disjoint set에서 union한다.
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			ds.union(from, to);
		}
		
		// disjoint set에 존재하는 집합의 개수를 센다
		int answer = ds.getNumberOfSets();
		
		System.out.println(answer);

	} // end main
}
