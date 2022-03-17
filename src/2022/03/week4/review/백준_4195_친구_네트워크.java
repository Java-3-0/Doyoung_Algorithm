// 57804KB, 528ms

package bj4195;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_F = 100000;
	static final int MAX_PEOPLE = 2 * MAX_F;

	static Map<String, Integer> nameToIdx = new HashMap<>();
	static DisjointSet ds = new DisjointSet();
	
	static class DisjointSet {
		/**
		 * i가 루트인 경우 parent[i]에는 음수값이 저장되어 있고 집합의 크기를 나타낸다. 
		 * i가 루트가 아닌 경우, parent[i]는 루트의 인덱스를 나타낸다 .
		 */
		int[] parent = new int[MAX_PEOPLE + 1];

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
			if (aRoot == bRoot && aRoot >= 0 && bRoot >= 0) {
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
			// a의 루트를 찾고
			int aRoot = find(a);

			// 그 크기가 parent[aRoot]에 음수로 저장되어 있는 것을 절대값을 취해서 리턴한다
			return Math.abs(parent[aRoot]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 전역변수 메모리 초기화
			nameToIdx.clear();
			ds.clear();

			// 친구 관계 수만큼 반복
			int F = Integer.parseInt(br.readLine());
			int idx = 0;
			for (int i = 0; i < F; i++) {
				// 두 이름 입력
				st = new StringTokenizer(br.readLine(), " ");
				String name1 = st.nextToken();
				String name2 = st.nextToken();

				// 새로운 이름을 만나면 그 이름에게 새로운 번호를 하나 할당해 준다.
				if (!nameToIdx.containsKey(name1)) {
					nameToIdx.put(name1, idx);
					idx++;
				}
				if (!nameToIdx.containsKey(name2)) {
					nameToIdx.put(name2, idx);
					idx++;
				}

				// 이름에 해당하는 번호를 맵에서 가져온다.
				int idxName1 = nameToIdx.get(name1);
				int idxName2 = nameToIdx.get(name2);

				// 두 번호를 union한다.
				ds.union(idxName1, idxName2);
				
				// 이 집합에 몇 명이 존재하는지 출력 스트링빌더에 추가한다.
				sb.append(ds.getSize(idxName1)).append("\n");

			} // end for i

		} // end for testCase

		// 결과 출력
		System.out.print(sb.toString());
		
	} // end main
}