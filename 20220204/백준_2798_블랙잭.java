// 블랙잭 문제
package baek2798;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int MAX_CARDS = 100;
	public static final int MAX_TARGET = 300000;

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);

		int numCards = sc.nextInt();
		int target = sc.nextInt();

		int[] cards = new int[numCards];
		for (int c = 0; c < numCards; c++) {
			cards[c] = sc.nextInt();
		}
		sc.close();

		// cards 배열 오름차순 정렬
		Arrays.sort(cards);

		// (target - sum)이 0 이상인 것 중 최소를 찾는다.
		// 중간에 sum이 이미 target을 초과해버린다면, 루프를 더 돌 필요가 없으니 빠져나온다.
		int minDiff = MAX_TARGET + 1;
		int answer = -1;
		firstLoop: for (int firstCard = 0; firstCard < numCards; firstCard++) {
			if (cards[firstCard] > target)
				break firstLoop;
			secondLoop: for (int secondCard = firstCard + 1; secondCard < numCards; secondCard++) {
				if (cards[firstCard] + cards[secondCard] > target)
					break secondLoop;
				for (int thirdCard = secondCard + 1; thirdCard < numCards; thirdCard++) {
					int sum = cards[firstCard] + cards[secondCard] + cards[thirdCard];
					int diff = target - sum;
					if (diff >= 0 && diff < minDiff) {
						minDiff = diff;
						answer = sum;
					}
				} // end for thirdCard
			} // end for secondCard
		} // end for firstCard

		System.out.println(answer);
	}
}
