// 점 찍기 문제
package baek2447;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (isBlank(y, x, N)) {
					sb.append(" ");
				} else {
					sb.append("*");
				}
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());

		sc.close();
	}

	public static boolean isBlank(int y, int x, int size) {
		// base case: 크기가 3이면 가운데 칸을 비운다
		if (size == 3) {
			if (y == 1 && x == 1) {
				return true;
			}
			return false;
		}

		// 다음 크기는 현재 크기의 1/3로, 다음 좌표도 그에 맞게 0 ~ (nextSize-1) 사이의 값으로 지정
		int nextSize = size / 3;
		int nextY = y % nextSize;
		int nextX = x % nextSize;

		// 가운데 칸이면 비우고, 가운데 칸이 아닌 경우 크기를 1/3로 줄여 재귀 호출
		if (y / nextSize == 1 && x / nextSize == 1) {
			return true;
		}

		else {
			return isBlank(nextY, nextX, nextSize);
		}

	}
}
