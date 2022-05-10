// 11576KB, 76ms

package bj1358;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int W, H, X, Y;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());

		int answer = 0;
		for (int i = 0; i < P; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			if (isInArea(x, y)) {
				answer++;
			}
		}

		System.out.println(answer);

	} // end main

	public static boolean isInArea(int x, int y) {
		// 반지름과 그 제곱을 미리 계산
		int radius = H / 2;
		int radiusSquare = radius * radius;

		// 직사각형 범위 내인 경우
		if (X <= x && x <= X + W && Y <= y && y <= Y + H) {
			return true;
		}

		// 왼쪽 원 범위 내인 경우
		int dy1 = y - (Y + radius);
		int dx1 = x - X;
		int distSquare1 = dy1 * dy1 + dx1 * dx1;
		if (distSquare1 <= radiusSquare) {
			return true;
		}

		// 오른쪽 원 범위 내인 경우
		int dy2 = y - (Y + radius);
		int dx2 = x - (X + W);
		int distSquare2 = dy2 * dy2 + dx2 * dx2;
		if (distSquare2 <= radiusSquare) {
			return true;
		}

		return false;
	}
}