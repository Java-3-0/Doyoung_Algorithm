// 11636KB, 80ms

package bj3005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	static final int MAX_H = 20, MAX_W = 20;

	static int H, W;
	static char[][] grid;
	static boolean[][] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		grid = new char[H][W];
		isVisited = new boolean[H][W];

		// 그리드 입력
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		TreeSet<String> set = new TreeSet<>();

		// 가로로 찾기
		initIsVisited();
		for (int startY = 0; startY < H; startY++) {
			for (int startX = 0; startX < W; startX++) {
				if (!isVisited[startY][startX] && grid[startY][startX] != '#') {
					String word = findWordHorizontal(startY, startX);
					if (word.length() >= 2) {
						set.add(word);
					}
				}
			}
		}

		// 세로로 찾기
		initIsVisited();
		for (int startY = 0; startY < H; startY++) {
			for (int startX = 0; startX < W; startX++) {
				if (!isVisited[startY][startX] && grid[startY][startX] != '#') {
					String word = findWordVertical(startY, startX);
					if (word.length() >= 2) {
						set.add(word);
					}
				}
			}
		}

		// 정답 출력
		String answer = set.first();

		System.out.println(answer);

	} // end main

	public static String findWordHorizontal(int startY, int startX) {
		StringBuilder sb = new StringBuilder();

		int y = startY;
		for (int x = startX; x < W; x++) {
			isVisited[y][x] = true;

			char c = grid[y][x];
			if (c == '#') {
				break;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String findWordVertical(int startY, int startX) {
		StringBuilder sb = new StringBuilder();

		int x = startX;
		for (int y = startY; y < H; y++) {
			isVisited[y][x] = true;

			char c = grid[y][x];
			if (c == '#') {
				break;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}