// 141004KB, 484ms

package bj20040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 500000;
	static final int MAX_M = 1000000;

	static class DisjointSet {
		int[] parent = new int[MAX_N];

		public DisjointSet() {
			super();
			Arrays.fill(this.parent, -1);
		}

		public int find(int a) {
			int pa = parent[a];

			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		public boolean union(int a, int b) {
			int rootA = find(a);
			int rootB = find(b);

			// 이미 같은 집합에 있는 경우 (사이클이 만들어지는 경우) false 리턴
			if (rootA == rootB) {
				return false;
			}

			// 다른 집합에 있는 경우 합치고, true 리턴
			parent[rootA] += parent[rootB];
			parent[rootB] = rootA;

			return true;
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// disjoint set 생성
		DisjointSet ds = new DisjointSet();
		
		// 차례마다 탐색하러 가기 전에 필요한 변수 선언
		int answer = 0;
		int lastIdx = 0;
		
		// disjoint set을 이용해서 처음으로 사이클이 생기는 지점을 찾아서 answer에 저장한다.
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			lastIdx = i;
			if (!ds.union(from, to)) {
				answer = i;
				break;
			}
		}
		
		// 남은 입력 그냥 받아와서 버림
		for (int i = lastIdx + 1; i <= M; i++) {
			br.readLine();
		}

		System.out.println(answer);

	} // end main
}