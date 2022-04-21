// 16372KB, 392ms

package bj2162;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	/** 위치 객체 */
	static class Position {
		long x;
		long y;

		public Position(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

	}

	/** 선분 객체 */
	static class Line {
		Position start;
		Position end;

		public Line(Position start, Position end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return "Line [start=" + start + ", end=" + end + "]";
		}

	}

	/** 상호 배타적 집합 객체 */
	static class DisjointSet {
		/**
		 * i가 루트인 경우 parent[i]에는 음수값이 저장되어 있고 집합의 크기를 나타낸다. i가 루트가 아닌 경우, parent[i]는 루트의
		 * 인덱스를 나타낸다 .
		 */
		int[] parent;

		/** 모든 원소이 자기 자신을 대표자로 가지는 상태로 DisjointSet을 생성하는 생성자 */
		public DisjointSet(int size) {
			super();

			parent = new int[size];

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
			if (aRoot == bRoot) {
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

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 선분 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 선분 배열 메모리 할당
		Line[] lines = new Line[N];

		// 유니온 파인드 셋 생성
		DisjointSet ds = new DisjointSet(N);

		// 위치 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x1 = Long.parseLong(st.nextToken());
			long y1 = Long.parseLong(st.nextToken());
			long x2 = Long.parseLong(st.nextToken());
			long y2 = Long.parseLong(st.nextToken());
			Position start = new Position(x1, y1);
			Position end = new Position(x2, y2);
			lines[i] = new Line(start, end);
		}
		
		// 만나는 선분들에 대해 유니온 연산 수행
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (isMeeting(lines[i], lines[j])) {
					ds.union(i, j);
				}
			}
		}
		
		Set<Integer> representatives = new HashSet<>();
		int maxGroupSize = 1;
		for (int i = 0; i < N; i++) {
			representatives.add(ds.find(i));
			maxGroupSize = Math.max(maxGroupSize, ds.getSize(i));
		}
		
		System.out.println(representatives.size());
		System.out.println(maxGroupSize);

	} // end main

	/** 세 점의 외적 계산 */
	public static long ccw(Position a, Position b, Position c) {
		long x1 = a.x;
		long x2 = b.x;
		long x3 = c.x;
		long y1 = a.y;
		long y2 = b.y;
		long y3 = c.y;

		long sum1 = x1 * y2 + x2 * y3 + x3 * y1;
		long sum2 = y1 * x2 + y2 * x3 + y3 * x1;
		long ret = sum1 - sum2;

		return ret;
	}

	/** 선분 l1와 l2가 만나는지 여부를 리턴 */
	public static boolean isMeeting(Line l1, Line l2) {
		Position p1 = l1.start;
		Position p2 = l1.end;
		Position p3 = l2.start;
		Position p4 = l2.end;

		int dir123 = Long.signum(ccw(p1, p2, p3));
		int dir124 = Long.signum(ccw(p1, p2, p4));

		int dir341 = Long.signum(ccw(p3, p4, p1));
		int dir342 = Long.signum(ccw(p3, p4, p2));

		// 교차하는 경우
		if (dir123 * dir124 < 0L && dir341 * dir342 < 0L) {
			return true;
		}

		// 3번 점이 선분12과 일직선상에 있는 경우
		if (dir123 == 0) {
			long minX = Math.min(p1.x, p2.x);
			long maxX = Math.max(p1.x, p2.x);
			long minY = Math.min(p1.y, p2.y);
			long maxY = Math.max(p1.y, p2.y);
			if (minX <= p3.x && p3.x <= maxX && minY <= p3.y && p3.y <= maxY) {
				return true;
			}
		}

		// 4번 점이 선분12과 일직선상에 있는 경우
		if (dir124 == 0) {
			long minX = Math.min(p1.x, p2.x);
			long maxX = Math.max(p1.x, p2.x);
			long minY = Math.min(p1.y, p2.y);
			long maxY = Math.max(p1.y, p2.y);
			if (minX <= p4.x && p4.x <= maxX && minY <= p4.y && p4.y <= maxY) {
				return true;
			}
		}

		// 1번 점이 선분34과 일직선상에 있는 경우
		if (dir341 == 0) {
			long minX = Math.min(p3.x, p4.x);
			long maxX = Math.max(p3.x, p4.x);
			long minY = Math.min(p3.y, p4.y);
			long maxY = Math.max(p3.y, p4.y);
			if (minX <= p1.x && p1.x <= maxX && minY <= p1.y && p1.y <= maxY) {
				return true;
			}
		}

		// 2번 점이 선분34과 일직선상에 있는 경우
		if (dir342 == 0) {
			long minX = Math.min(p3.x, p4.x);
			long maxX = Math.max(p3.x, p4.x);
			long minY = Math.min(p3.y, p4.y);
			long maxY = Math.max(p3.y, p4.y);
			if (minX <= p2.x && p2.x <= maxX && minY <= p2.y && p2.y <= maxY) {
				return true;
			}
		}

		// 그 외의 경우 만나지 않는다
		return false;
	}

}