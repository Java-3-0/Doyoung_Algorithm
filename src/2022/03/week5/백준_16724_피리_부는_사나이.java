// 26812KB, 312ms

package bj16724;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 1000, MAX_W = 1000;

	static char[][] grid = new char[MAX_H][MAX_W];
	static int H, W;

	static class DisjointSet {
		int[] parent;

		/** 원소의 개수를 입력받아서, parent[] 배열을 할당하고, 모든 원소의 parent를 -1로 초기화하는 생성자 */
		public DisjointSet(int size) {
			super();
			parent = new int[size];
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

		public int getNumberOfSets() {
			int ret = 0;

			for (int p : parent) {
				if (p < 0) {
					ret++;
				}
			}

			return ret;

		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			char[] line = br.readLine().toCharArray();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line[x];
			}
		}

		// DisjointSet 생성
		DisjointSet ds = new DisjointSet(H * W);

		// union 연산 수행
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				char dir = grid[y][x];
				int idx1 = W * y + x;
				int idx2 = getArrowIdx(idx1, dir);

				ds.union(idx1, idx2);
			}
		}

        // 정답 계산 및 출력
		int answer = ds.getNumberOfSets();
		System.out.println(answer);

	} // end main

	public static int getArrowIdx(int idx, char dir) {
		switch (dir) {
		case 'L':
			return idx - 1;
		case 'D':
			return idx + W;
		case 'U':
			return idx - W;
		case 'R':
			return idx + 1;
		default:
			return -1;
		}

	}
}