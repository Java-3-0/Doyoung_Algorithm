// 11848KB, 80ms

package bj7869;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final double PI = Math.PI;

	static class Circle implements Comparable<Circle> {
		double x;
		double y;
		double radius;

		public Circle(double x, double y, double radius) {
			super();
			this.x = x;
			this.y = y;
			this.radius = radius;
		}

		@Override
		public int compareTo(Circle c) {
			return Double.compare(this.radius, c.radius);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 원 두 개를 저장할 배열
		Circle[] circles = new Circle[2];

		// 원 두 개 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < 2; i++) {
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			double radius = Double.parseDouble(st.nextToken());
			circles[i] = new Circle(x, y, radius);
		}

		// circles[0]가 circles[1]보다 작은 원이 되도록 정렬
		Arrays.sort(circles);

		// 접하는 영역 넓이 계산
		double answer = getIntersectArea(circles[0], circles[1]);

		// 소숫점 이하 3자리까지 출력
		System.out.printf("%.3f\n", answer);

	} // end main

	public static double getIntersectArea(Circle small, Circle big) {
		double x1 = small.x;
		double y1 = small.y;
		double r1 = small.radius;

		double x2 = big.x;
		double y2 = big.y;
		double r2 = big.radius;

		// 중심 간의 거리 계산
		double distX = Math.abs(x2 - x1);
		double distY = Math.abs(y2 - y1);
		double d = Math.sqrt(square(distX) + square(distY));

		// 두 원이 외접하거나 만나지 않을 경우
		if (r1 + r2 <= d) {
			return 0.0;
		}

		// 두 원이 내접하거나, 한 원이 안쪽에 있는 경우
		else if (r1 + d <= r2) {
			return square(r1) * PI;
		}

		// 두 원이 일부만 겹치는 경우
		else {
			double theta1 = 2.0 * Math.acos((square(r1) + square(d) - square(r2)) / (2.0 * r1 * d));
			double theta2 = 2.0 * Math.acos((square(r2) + square(d) - square(r1)) / (2.0 * r2 * d));

			double sector1 = square(r1) * theta1 / 2.0;
			double triangle1 = square(r1) * Math.sin(theta1) / 2.0;
			double area1 = sector1 - triangle1;
			
			double sector2 = square(r2) * theta2 / 2.0;
			double triangle2 = square(r2) * Math.sin(theta2) / 2.0;
			double area2 = sector2 - triangle2;

			return area1 + area2;
		}
	}

	public static double square(double x) {
		return x * x;
	}
}