// 42812KB, 360ms

package bj20955;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N, M;

	static class DisjointSet {
		int[] parent = new int[MAX_N + 1];

		/** 모든 원소의 대표자를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			Arrays.fill(parent, -1);
		}

		/** a의 대표자를 찾는다 */
		public int find(int a) {
			// 자기 자신이 대표자인 경우 그대로 리턴한다.
			if (parent[a] == -1) {
				return a;
			}

			// path compression을 적용하면서 재귀적으로 대표자를 찾는다.
			return parent[a] = find(parent[a]);
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
			parent[bRoot] = aRoot;
			return true;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		DisjointSet ds = new DisjointSet();
		
		int answer = 0;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			// 사이클이 생기면 끊어야 한다.
			if (!ds.union(u, v)) {
				answer++;
			}
		}
		
		// 집합 개수 - 1번의 합치기 연산으로 모든 집합을 하나로 합쳐야 한다.
		Set<Integer> roots = new HashSet<>();
		for (int i = 1; i <= N; i++) {
			roots.add(ds.find(i));
		}
		answer += roots.size() - 1;
		
		// 정답 출력
		System.out.println(answer);
		
	} // end main

}