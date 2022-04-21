// 11528KB, 76ms

package bj17386;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		long x1 = Long.parseLong(st.nextToken());
		long y1 = Long.parseLong(st.nextToken());
		long x2 = Long.parseLong(st.nextToken());
		long y2 = Long.parseLong(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		long x3 = Long.parseLong(st.nextToken());
		long y3 = Long.parseLong(st.nextToken());
		long x4 = Long.parseLong(st.nextToken());
		long y4 = Long.parseLong(st.nextToken());

		Position p1 = new Position(x1, y1);
		Position p2 = new Position(x2, y2);
		Position p3 = new Position(x3, y3);
		Position p4 = new Position(x4, y4);

		// 정답 출력
		if (isMeeting(p1, p2, p3, p4)) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}

	} // end main

	/** 선분 p1p2와 p3p4가 만나는지 여부를 리턴 */
	public static boolean isMeeting(Position p1, Position p2, Position p3, Position p4) {
		int dir123 = Long.signum(ccw(p1, p2, p3));
		int dir124 = Long.signum(ccw(p1, p2, p4));

		int dir341 = Long.signum(ccw(p3, p4, p1));
		int dir342 = Long.signum(ccw(p3, p4, p2));

		if (dir123 * dir124 <= 0L && dir341 * dir342 <= 0L) {
			return true;
		} else {
			return false;
		}
	}

}