// 11792KB, 80ms

package bj10158;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int width = Integer.parseInt(st.nextToken());
		int height = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		int startX = Integer.parseInt(st.nextToken());
		int startY = Integer.parseInt(st.nextToken());
		long time = Long.parseLong(br.readLine());
		br.close();

		// time이 지난 후의 x좌표와 y좌표를 계산해서 width * 2, height * 2 범위 내로 들어오게 만듦
		int x = (int) ((startX + time) % (2 * width));
		int y = (int) ((startY + time) % (2 * height));

		// 이 좌표가 width, height 범위를 넘어가면 반사된 좌표를 계산
		if (x > width) {
			x = width - x % width;
		}
		if (y > height) {
			y = height - y % height;
		}

		// 출력
		System.out.printf("%d %d\n", x, y);
	}

}
