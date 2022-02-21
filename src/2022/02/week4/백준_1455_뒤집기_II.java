// 11608KB, 92ms

package bj1455;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());

		// 동전들의 초기 상태 입력
		boolean[][] isFront = new boolean[height][width];
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				char c = line.charAt(x);
				isFront[y][x] = (c == '0' ? true : false);
			}
		}

		boolean[] isFlippedColumn = new boolean[width];
		Arrays.fill(isFlippedColumn, false);

		int count = 0;
		for (int startY = height - 1; startY >= 0; startY--) {
			for (int startX = width - 1; startX >= 0; startX--) {
				// 현재 앞면이면 지나침
				if (isFlippedColumn[startX] ^ isFront[startY][startX]) {
					continue;
				}
				// 현재 뒷면이면 이곳부터 왼쪽 열들을 모두 뒤집음
				else {
					count++;
					for (int x = 0; x <= startX; x++) {
						isFlippedColumn[x] = !isFlippedColumn[x];
					}
				}

			}
		}

		System.out.println(count);

	}
}