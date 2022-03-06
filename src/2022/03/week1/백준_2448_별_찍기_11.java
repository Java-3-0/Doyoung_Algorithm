// 236500KB, 524ms

package bj2448;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 3 * (int) Math.pow(2, 10);

	static int N;
	static char[][] grid = new char[MAX_N][MAX_N * 2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 그리드 공백으로 초기화
		initGrid();
		
		// N 입력
		N = Integer.parseInt(br.readLine());

		// 출력할 삼각형의 별모양들을 채운다.
		fill(0, N - 1, N);

		// 출력 스트링빌더에 추가
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < 2 * N; x++) {
				sb.append(grid[y][x]);
			}
			
			// 줄바꿈
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	public static void initGrid() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], ' ');
		}
	}

	public static void fill(int startY, int startX, int size) {
		if (size < 3) {
			return;
		}
		
		int nextSize = size / 2;

		// 왼쪽 모서리와 오른쪽 모서리를 칠한다.
		for (int dy = 0, dx = 0; dy < size && dx < size; dy++, dx++) {
			grid[startY + dy][startX + dx] = '*';
			grid[startY + dy][startX - dx] = '*';
		}

		// 아래 모서리를 칠한다.
		int lastY = startY + (size - 1);
		int lastX1 = startX - (size - 1);
		int lastX2 = startX + (size - 1);
		for (int x = lastX1, i = 0; x <= lastX2; x++) {
			if (i == 5) {
				grid[lastY][x] = ' ';
				i = 0;
			} else {
				grid[lastY][x] = '*';
				i++;
			}
		}
		
		
		int midY = startY + nextSize;
		int midX1 = startX - nextSize;
		int midX2 = startX + nextSize;
		
		fill(startY, startX, nextSize);
		fill(midY, midX1, nextSize);
		fill(midY, midX2, nextSize);
	}
}