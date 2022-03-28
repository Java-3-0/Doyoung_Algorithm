package bj18116;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;

	static int N;

	static class DisjointSet {
		int[] parent;

		/** 원소의 개수를 입력받아서, parent[] 배열을 할당하고, 모든 원소의 parent를 -1로 초기화하는 생성자 */
		public DisjointSet(int size) {
			super();
			parent = new int[size + 1];
			Arrays.fill(parent, -1);
		}

		/** 파인드 연산 수행 */
		public int find(int a) {
			int pa = parent[a];
			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		/** 유니온 연산 수행 */
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

		/** a가 속한 집합의 크기를 리턴 */
		public int getSize(int a) {
			return -parent[find(a)];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		DisjointSet ds = new DisjointSet(MAX_N);

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			char type = st.nextToken().charAt(0);
			if (type == 'I') {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				ds.union(a, b);
			} else {
				int a = Integer.parseInt(st.nextToken());
				int answer = ds.getSize(a);
				sb.append(answer).append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

}