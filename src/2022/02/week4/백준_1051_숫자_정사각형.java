package bj1051;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_NUM = 99;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());

		// 그리드 입력
		int[][] grid = new int[height][width];
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				grid[y][x] = line.charAt(x) - '0';
			}
		}

		// 꼭지점의 수가 모두 같은 정사각형들의 area들을 담을 set
		TreeSet<Integer> areas = new TreeSet<>();

		// 가능한 모든 (y1, x1), (y2, x2) 쌍에 대해 완전탐색
		for (int y1 = 0; y1 < height; y1++) {
			for (int x1 = 0; x1 < width; x1++) {
				for (int y2 = y1; y2 < height; y2++) {
					for (int x2 = x1; x2 < width; x2++) {
						// 모든 꼭지점의 값이 같은 정사각형을 만날 때마다 그 넓이를 계산해서 set에 넣는다.
						int topLeft = grid[y1][x1];
						if (y2 - y1 == x2 - x1 && grid[y1][x2] == topLeft && grid[y2][x1] == topLeft && grid[y2][x2] == topLeft) {
							int area = getArea(y1, x1, y2, x2);

							areas.add(area);
						}
					}
				}
			}
		} // end 4중 for문

		// set에서 가장 큰 것 넓이를 구한다.
		int maxArea = areas.last();
		
		// 출력
		System.out.println(maxArea);

	} // end main

	public static int getArea(int y1, int x1, int y2, int x2) {
		return (x2 - x1 + 1) * (y2 - y1 + 1);
	}
}