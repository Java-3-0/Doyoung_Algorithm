// 19372KB, 172ms

package bj2166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	/** 위치 객체 */
	static class Position {
		double x;
		double y;

		public Position(double x, double y) {
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
	public static double ccw(Position a, Position b, Position c) {
		double x1 = a.x;
		double x2 = b.x;
		double x3 = c.x;
		double y1 = a.y;
		double y2 = b.y;
		double y3 = c.y;

		double sum1 = x1 * y2 + x2 * y3 + x3 * y1;
		double sum2 = y1 * x2 + y2 * x3 + y3 * x1;
		double ret = sum1 - sum2;

		return ret;
	}

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 점 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 좌표 입력
		Position[] positions = new Position[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 면적 계산
		double totalArea = 0.0;
		for (int i = 2; i < N; i++) {
			double area = 0.5 * ccw(positions[0], positions[i - 1], positions[i]);
			totalArea += area;
		}
		totalArea = Math.abs(totalArea);

		// 출력
		System.out.printf("%.1f\n", totalArea);

	} // end main

}