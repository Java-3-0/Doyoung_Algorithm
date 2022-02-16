// 12964KB, 100ms

package bj1002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 0; t < T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");

			double x1 = Double.parseDouble(st.nextToken());
			double y1 = Double.parseDouble(st.nextToken());
			double r1 = Double.parseDouble(st.nextToken());
			double x2 = Double.parseDouble(st.nextToken());
			double y2 = Double.parseDouble(st.nextToken());
			double r2 = Double.parseDouble(st.nextToken());

			// 터렛들 사이의 위치 계산
			double dist = getDistance(x1, y1, x2, y2);

			int answer = 0;

			double radiusSum = r1 + r2;
			double radiusDiff = r1 < r2 ? r2 - r1 : r1 - r2;

			// 두 원이 일치하는 경우
			if (dist == 0 && r1 == r2) {
				answer = -1;
			}
			
			// 내접 or 외접
			else if (dist == radiusDiff || dist == radiusSum) {
				answer = 1;
			}
			
			// 만나지 않는 경우
			else if (dist > radiusSum || dist < radiusDiff) {
				answer = 0;
			}
			// 이외의 경우에는 두 점에서 만남
			else {
				answer = 2;
			}
			
			// 정답 출력
			System.out.println(answer);
		}
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double yDist = y2 - y1;
		double xDist = x2 - x1;

		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
}
