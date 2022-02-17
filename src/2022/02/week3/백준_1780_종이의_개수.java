// 314276KB, 780ms

package bj1780;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 2187;
	static int[][] grid = new int[MAX_N][MAX_N];
	static int[] answers = new int[3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		int N = Integer.parseInt(br.readLine());

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		countPapers(0, 0, N);

		for (int num : answers) {
			System.out.println(num);
		}
	}

	/** 종이의 개수를 세서 answers[] 배열을 업데이트 한다 */
	public static void countPapers(int startY, int startX, int size) {
		int firstNum = grid[startY][startX];

		// 종이 전체가 다 같으면 한장으로 센다
		if (isAllSame(startY, startX, size)) {
			answers[firstNum + 1]++;
			return;
		}

		int nextSize = size / 3;
		// 다르면 1/9씩 나눠서 재귀 호출
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				countPapers(startY + y * nextSize, startX + x * nextSize, nextSize);
			}
		}

		return;
	}

	/** (startY, startX) 부터 오른쪽 아래로 size * size 공간의 내용물이 같다면 true, 아니면 false 리턴 */
	public static boolean isAllSame(int startY, int startX, int size) {
		int firstNum = grid[startY][startX];

		for (int dy = 0; dy < size; dy++) {
			for (int dx = 0; dx < size; dx++) {
				int y = startY + dy;
				int x = startX + dx;
				if (grid[y][x] != firstNum) {
					return false;
				}
			}
		}

		return true;
	}
}