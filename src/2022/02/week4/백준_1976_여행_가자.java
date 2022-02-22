// 15752KB, 136ms

package bj1976;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200;
	static final int MAX_M = 1000;

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
			if (parent[a] < 0) {
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
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		DisjointSet djs = new DisjointSet();
		
		// 연결 여부 입력받아서 연결된 곳마다 djs에서 union
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				int connected = Integer.parseInt(st.nextToken());
				if (connected == 1) {
					djs.union(y, x);
				}
			}
		}
		
		// 방문할 경로 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] path = new int[M];
		for (int i = 0; i < M; i++) {
			path[i] = Integer.parseInt(st.nextToken());
		}
		
		// 방문 가능한지 파악해서 결과 프린트
		int root = djs.find(path[0]);
		for (int i = 1; i < M; i++) {
			if (djs.find(path[i]) != root) {
				System.out.println("NO");
				return;
			}
		}
		
		System.out.println("YES");
	}
}