// 11760KB, 80ms

package bj2699;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	/** 위치 객체 */
	static class Position implements Comparable<Position> {
		long x;
		long y;

		public Position(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Position p) {
			if (this.y == p.y) {
				return Long.compare(this.x, p.x);
			}
			return -Long.compare(this.y, p.y);
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

	}

	/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
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

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			int N = Integer.parseInt(br.readLine());

			Position[] positions = new Position[N];

			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				if (i != 0 && i % 5 == 0) {
					st = new StringTokenizer(br.readLine(), " ");
				}
				long x = Long.parseLong(st.nextToken());
				long y = Long.parseLong(st.nextToken());
				positions[i] = new Position(x, y);
			}

			List<Position> answers = getConvexHull(positions);

			int numPoints = answers.size();
			sb.append(numPoints).append("\n");

			for (Position p : answers) {
				sb.append(p.x).append(" ").append(p.y).append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	public static List<Position> getConvexHull(Position[] positions) {
		int len = positions.length;

		// 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
		Arrays.sort(positions);

		// 스택 생성
		Stack<Position> lower = new Stack<>();
		Stack<Position> upper = new Stack<>();

		// 아래 껍질 계산
		for (int i = 0; i < len; i++) {
			while (lower.size() > 1
					&& (ccw(lower.get(lower.size() - 2), lower.get(lower.size() - 1), positions[i]) >= 0)) {
				lower.pop();
			}
			lower.add(positions[i]);
		}

		// 위 껍질 계산
		for (int i = len - 1; i >= 0; i--) {
			while (upper.size() > 1
					&& (ccw(upper.get(upper.size() - 2), upper.get(upper.size() - 1), positions[i]) >= 0)) {
				upper.pop();
			}
			upper.add(positions[i]);
		}

		// 시작점, 끝점에서의 중복 제거
		lower.pop();
		upper.pop();

		// 아래 껍질과 위 껍질을 합쳐서 정답 계산
		List<Position> ret = new ArrayList<>();
		ret.addAll(lower);
		ret.addAll(upper);

		return ret;
	}

}