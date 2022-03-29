// 168636KB, 752ms

package bj7511;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final int MAX_K = 100000;

	static int N, K;

	static class DisjointSet {
		int[] parent;

		public DisjointSet(int size) {
			super();
			parent = new int[size + 1];
		}

		public void claer() {
			int len = parent.length;
			for (int i = 0; i < len; i++) {
				parent[i] = i;
			}
		}

		public int find(int a) {
			int pa = parent[a];
			if (pa == a) {
				return a;
			}

			return parent[a] = find(pa);
		}

		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);
			if (aRoot == bRoot) {
				return false;
			}

			parent[bRoot] = aRoot;
			return true;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		DisjointSet ds = new DisjointSet(MAX_N);

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// DisjointSet 초기화
			ds.claer();

			// N, K 입력
			N = Integer.parseInt(br.readLine());
			K = Integer.parseInt(br.readLine());

			// 친구 관계 입력
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				ds.union(a, b);
			}

			// sb에 "scenario i:" 추가
			sb.append("Scenario ").append(testCase).append(":").append("\n");

			// M개의 쿼리 수행
			int M = Integer.parseInt(br.readLine());
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				if (ds.find(u) == ds.find(v)) {
					sb.append(1).append("\n");
				} else {
					sb.append(0).append("\n");
				}
			}

			// sb에 빈 줄 추가
			sb.append("\n");

		} // end for testCase

		System.out.print(sb.toString());

	} // end main

}