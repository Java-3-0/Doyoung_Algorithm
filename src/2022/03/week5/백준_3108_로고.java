// 13052KB, 136ms

package bj3108;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static int N;

	static class Rectangle {
		int left;
		int right;
		int bottom;
		int top;

		public Rectangle(int x1, int y1, int x2, int y2) {
			super();
			this.left = Math.min(x1, x2);
			this.right = Math.max(x1, x2);
			this.bottom = Math.min(y1, y2);
			this.top = Math.max(y1, y2);
		}

		/** 직사각형 r과 선이 만나는지 여부를 리턴 */
		public boolean isMeeting(Rectangle r) {
			// 멀리 떨어져 있는 경우
			if (this.right < r.left || this.left > r.right || this.top < r.bottom || this.bottom > r.top) {
				return false;
			}

			// this가 r의 내부에 있는 경우
			if ((r.left < this.left && this.left < r.right && r.left < this.right && this.right < r.right)
					&& (r.bottom < this.bottom && this.bottom < r.top && r.bottom < this.top && this.top < r.top)) {
				return false;
			}

			// r이 this의 내부에 있는 경우
			if ((this.left < r.left && r.left < this.right && this.left < r.right && r.right < this.right)
					&& (this.bottom < r.bottom && r.bottom < this.top && this.bottom < r.top && r.top < this.top)) {
				return false;
			}

			return true;
		}

		/** 시작 위치에서 바로 그릴 수 있는지 여부를 리턴 */
		public boolean canStart() {
			if (this.left == 0 || this.right == 0) {
				if (this.bottom <= 0 && this.top >= 0) {
					return true;
				}
				return false;
			}
			if (this.bottom == 0 || this.top == 0) {
				if (this.left <= 0 && this.right >= 0) {
					return true;
				}
				return false;
			}

			return false;
		}
	}

	static class DisjointSet {
		int[] parent;

		/** 모든 원소이 자기 자신을 대표자로 가지는 상태로 DisjointSet을 생성하는 생성자 */
		public DisjointSet(int size) {
			super();

			parent = new int[size];
			Arrays.fill(parent, -1);
		}

		/** DisjointSet을 초기화한다 */
		public void clear() {
			Arrays.fill(parent, -1);
		}

		/** a가 속해있는 집합의 root를 찾아서 리턴한다 */
		public int find(int a) {
			int pa = parent[a];

			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		/** a가 속해있는 집합과 b가 속해있는 집합을 합친다 */
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

		/** a가 속해 있는 집합의 크기를 리턴 */
		public int getSize(int a) {
			// a의 루트를 찾고
			int aRoot = find(a);

			// 그 크기가 parent[aRoot]에 음수로 저장되어 있는 것을 절대값을 취해서 리턴한다
			return Math.abs(parent[aRoot]);
		}
	}

	static Rectangle[] rectangles;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 직사각형 개수 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		rectangles = new Rectangle[N];

		int answer = 1;
		// 직사각형 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			rectangles[i] = new Rectangle(x1, y1, x2, y2);
			if (rectangles[i].canStart()) {
				answer = 0;
			}
		}

		// 유니온 파인드 셋 생성
		DisjointSet ds = new DisjointSet(N);

		// 직사각형이 만나는 관계에 따라 유니온 연산 수행
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (rectangles[i].isMeeting(rectangles[j])) {
					ds.union(i, j);
				}
			}
		}

		// 추가로 연필을 떼어야 하는 횟수 계산
		Set<Integer> representatives = new HashSet<>();
		for (int i = 0; i < N; i++) {
			representatives.add(ds.find(i));
		}

		answer += (representatives.size() - 1);
		
		// 정답 출력
		System.out.println(answer);

	} // end main

}