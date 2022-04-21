// 59292KB, 756ms

package bj4181;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static List<Position> positions = new ArrayList<Position>();
	static final long INF = 9876543219876543L;

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
			if (this.x == p.x) {
				return Long.compare(this.y, p.y);
			}
			return Long.compare(this.x, p.x);
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

		final int N = Integer.parseInt(br.readLine());

		// 좌표 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			char c = st.nextToken().charAt(0);
			if (c == 'Y') {
				positions.add(new Position(x, y));
			}
		}
		int len = positions.size();

		// 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
		Collections.sort(positions);

		// 스택 생성
		Stack<Position> lower = new Stack<>();
		Stack<Position> upper = new Stack<>();

		// 아래 껍질 계산
		for (int i = 0; i < len; i++) {
			while (lower.size() > 1
					&& (ccw(lower.get(lower.size() - 2), lower.get(lower.size() - 1), positions.get(i)) < 0)) {
				lower.pop();
			}
			lower.add(positions.get(i));
		}

		// 위 껍질 계산
		for (int i = len - 1; i >= 0; i--) {
			while (upper.size() > 1
					&& (ccw(upper.get(upper.size() - 2), upper.get(upper.size() - 1), positions.get(i)) < 0)) {
				upper.pop();
			}
			upper.add(positions.get(i));
		}

		// 시작점, 끝점에서의 중복 제거
		lower.pop();
		upper.pop();

		// 아래 껍질과 위 껍질을 합쳐서 정답 계산
		List<Position> answers = new ArrayList<>();
		answers.addAll(lower);
		answers.addAll(upper);

		// 출력
		StringBuilder sb = new StringBuilder();

		int numPoints = answers.size();
		sb.append(numPoints).append("\n");

		for (Position p : answers) {
			sb.append(p.x).append(" ").append(p.y).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}