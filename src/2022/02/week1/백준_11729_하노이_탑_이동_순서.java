// 하노이 탑 문제
package baek11729;

import java.util.Scanner;

public class Main {
	// 초기화
	public static int countMoves = 0; // 하노이 이동 횟수
	public static StringBuilder sb = new StringBuilder(); // 하노이 이동 과정

	// 메인
	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		sc.close();

		// 하노이 수행
		hanoi(N, 1, 3);

		// 출력
		System.out.println(countMoves);
		System.out.println(sb.toString());
	}

	// 하노이 수행하면서 이동 과정은 sb에, 이동 횟수는 count에 저장
	public static void hanoi(int N, int start, int finish) {
		if (N == 1) {
			countMoves++;
			sb.append(start).append(" ").append(finish).append("\n");
		} else {
			int other = otherTower(start, finish);
			hanoi(N - 1, start, other);
			hanoi(1, start, finish);
			hanoi(N - 1, other, finish);
		}
	}

	// tower1, tower2를 제외한 나머지 한 타워의 번호 리턴
	public static int otherTower(int tower1, int tower2) {
		switch (tower1 + tower2) {
		case 3:
			return 3;
		case 4:
			return 2;
		case 5:
			return 1;
		default:
			return -1;
		}
	}
}