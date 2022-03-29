// 32348KB, 252ms

package bj17090;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 500, MAX_W = 500;
	static final int OUT = -1;

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];

	static class DisjointSet {
		int[] parent;

		/** H * W 크기의 parent[] 배열을 할당하고, 모든 원소의 parent를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			int size = H * W;
			parent = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;

			}
		}

		/** 파인드 연산 수행 */
		public int find(int a) {
			int pa = parent[a];
			if (pa == OUT || pa == a) {
				return a;
			}

			return parent[a] = find(pa);
		}

		public int getParent(int a) {
			return parent[a];
		}

		/** 유니온 연산 수행 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			if (aRoot == bRoot) {
				return false;
			}

			parent[aRoot] = bRoot;

			return true;

		}

		public void setOut(int a) {
			int aRoot = find(a);
			parent[aRoot] = OUT;
		}
	}

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		public int toInt() {
			return W * this.y + this.x;
		}

		public Position getArrowPos(char dir) {
			switch (dir) {
			case 'U':
				return new Position(this.y - 1, this.x);
			case 'D':
				return new Position(this.y + 1, this.x);
			case 'L':
				return new Position(this.y, this.x - 1);
			case 'R':
				return new Position(this.y, this.x + 1);
			default:
				return null;
			}
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}

			return false;
		}
	}

	/** (y, x)에서 다른 칸을 거치지 않고 바로 맵 밖으로 나갈 수 있으면 true를 리턴, 아니면 false를 리턴 */

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

		// 상호 배타적 집합 생성
		DisjointSet ds = new DisjointSet();

		// 유니온 연산 수행
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				char dir = grid[y][x];

				Position p1 = new Position(y, x);
				Position p2 = p1.getArrowPos(dir);
				if (!p2.isInRange()) {
					int idx1 = p1.toInt();
					ds.setOut(idx1);
				} else {
					int idx1 = p1.toInt();
					int idx2 = p2.toInt();
					ds.union(idx1, idx2);
				}
			}
		}

		// 탈출할 수 있는 칸 수 파악
		int answer = 0;
		for (int i = 0; i < H * W; i++) {
			if (ds.getParent(ds.find(i)) == OUT) {
				answer++;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

}