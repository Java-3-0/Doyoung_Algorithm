// 43880KB, 628ms

package bj1708;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static Position[] positions;
	static Position startPos;
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
			long ccw = ccw(startPos, this, p);

			// ccw 값이 0이면 거리가 가까운 순으로 정렬
			if (ccw == 0L) {
				long d1 = getDistSquare(startPos, this);
				long d2 = getDistSquare(startPos, p);
				return Double.compare(d1, d2);
			}

			else if (ccw > 0L) {
				return -1;
			} else {
				return 1;
			}
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

	public static long getDistSquare(Position a, Position b) {
		long dx = Math.abs(b.x - a.x);
		long dy = Math.abs(b.y - a.y);
		return dx * dx + dy * dy;
	}

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		final int N = Integer.parseInt(br.readLine());

		// 좌표 입력
		positions = new Position[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 좌측 하단 점 좌표 계산
		long minX = INF;
		long minY = INF;
		for (Position pos : positions) {
			if (pos.x == minX) {
				if (pos.y < minY) {
					minY = pos.y;
				}
			} else if (pos.x < minX) {
				minX = pos.x;
				minY = pos.y;
			}
		}

		startPos = new Position(minX, minY);

		// 좌표 ccw 기준 정렬
		Arrays.sort(positions);

		// 스택 생성
		Stack<Position> stack = new Stack<>();

		// 그라함 스캔 알고리즘
		stack.push(startPos);
		for (int i = 1; i < N; i++) {
			Position now = positions[i];

			while (stack.size() >= 2) {
				Position prev2 = stack.get(stack.size() - 2);
				Position prev = stack.peek();

				long ccwVal = ccw(prev2, prev, now);
				if (ccwVal <= 0L) {
					stack.pop();
				} else {
					break;
				}
			}

			stack.push(now);
		}

		// 정답 계산 및 출력
		int answer = stack.size();

		System.out.println(answer);

	} // end main

}