// 13052KB, 108ms

package bj2630;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 128;
	public static int[][] board = new int[MAX_N][MAX_N];
	public static int[] count = new int[2];

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				board[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// count 갱신
		countPapers(0, 0, N);
		
		// 출력
		System.out.println(count[0]);
		System.out.println(count[1]);
	}

	/** (startY, startX)부터 size * size 공간의 범위를 탐색하면서 색종이 개수 갱신 */
	public static void countPapers(int startY, int startX, int size) {
		int firstColor = board[startY][startX];

		// 한 가지 색이라면 색종이 하나로 만들고 개수 갱신 후 리턴
		if (isAllSameColor(startY, startX, size)) {
			count[firstColor]++;
			return;
		}

		// 여러 색이라면 1/4씩 나눠서 재귀 호출
		int nextSize = size / 2;
		countPapers(startY, startX, nextSize);
		countPapers(startY, startX + nextSize, nextSize);
		countPapers(startY + nextSize, startX, nextSize);
		countPapers(startY + nextSize, startX + nextSize, nextSize);

		return;
	}

	public static boolean isAllSameColor(int startY, int startX, int size) {
		int firstColor = board[startY][startX];

		for (int dy = 0; dy < size; dy++) {
			for (int dx = 0; dx < size; dx++) {
				if (board[startY + dy][startX + dx] != firstColor) {
					return false;
				}
			}
		}

		return true;
	}

}
