// 88676KB, 432ms

package bj17352;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300000;

	static int N;

	static class DisjointSet {
		int[] parent;

		public DisjointSet(int size) {
			super();
			parent = new int[size + 1];
			for (int i = 1; i <= size; i++) {
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

		N = Integer.parseInt(br.readLine());

		DisjointSet ds = new DisjointSet(N);
		for (int i = 0; i < N - 2; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			ds.union(a, b);
		}

		int first = ds.find(1);
		for (int i = 2; i <= N; i++) {
			int second = ds.find(i);
			if (first != second) {
				System.out.println(first + " " + second);
				break;
			}
		}

	} // end main

}