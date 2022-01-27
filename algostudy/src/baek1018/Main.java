// 체스판 다시 칠하기 문제
package baek1018;

import java.util.Scanner;

public class Main {
	public static final int CHESSBOARD_SIZE = 8;
	public static final int MAX_HEIGHT = 50;
	public static final int MAX_WIDTH = 50;
	public static int height;
	public static int width;
	public static int[][] board;

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		height = sc.nextInt();
		width = sc.nextInt();
		sc.nextLine();

		board = new int[height][width];
		for (int y = 0; y < height; y++) {
			String buf = sc.nextLine();
			for (int x = 0; x < width; x++) {
				if (buf.charAt(x) == 'W') {
					board[y][x] = 1;
				}
			} // end for x
		} // end for y

		sc.close();

		// 다시 칠해야 하는 최소 횟수를 계산
		int minRepaint = Integer.MAX_VALUE;
		for (int y = 0; y < height - CHESSBOARD_SIZE + 1; y++) {
			for (int x = 0; x < width - CHESSBOARD_SIZE + 1; x++) {
				minRepaint = Math.min(minRepaint, countRepaint(y, x));
			} // end for x
		} // end for y

		// 출력
		System.out.println(minRepaint);
	}

	/**
	 * 좌상단의 포지션에서부터 8x8 영역을 탐색하면서, 다시 칠해야 되는 칸 수를 리턴.
	 * 
	 * @param startY : 시작 포지션의 y좌표
	 * @param startX : 시작 포지션의 x좌표
	 * @return 다시 칠해야 하는 칸 수
	 */
	public static int countRepaint(int startY, int startX) {
		int count = 0;
		// 한 방법으로 칠했을 경우를 카운트
		for (int y = startY; y < startY + CHESSBOARD_SIZE; y++) {
			for (int x = startX; x < startX + CHESSBOARD_SIZE; x++) {
				if ((x + y) % 2 == 0 && board[y][x] == 1) {
					count++;
				} else if ((x + y) % 2 == 1 && board[y][x] == 0) {
					count++;
				}
			}
		}

		// 이렇게 칠하는 것과 반대로 칠하는 것 중 횟수가 작은 쪽을 리턴
		return Math.min(count, CHESSBOARD_SIZE * CHESSBOARD_SIZE - count);
	}
}
