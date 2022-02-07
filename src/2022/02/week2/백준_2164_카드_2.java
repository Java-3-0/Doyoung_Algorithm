// 48988KB, 276ms

package baek2164;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		sc.close();
		
		int answer = lastCard(N);
		System.out.println(answer);
	}

	/** 마지막 남게 되는 카드의 수 값을 리턴 */
	public static int lastCard(int N) {
		Queue<Integer> cards = new LinkedList<>();

		// 큐에 모든 카드를 담음
		for (int num = 1; num <= N; num++) {
			cards.add(num);
		}
		
		// 한 장 남을 때까지 반복
		while (cards.size() > 1) {
			// 한 장을 버리기
			cards.poll();
			
			// 한 장을 제일 뒤로 옮기기
			cards.add(cards.poll());
		}

		// 마지막 남은 카드를 리턴
		return cards.peek();
	}
}
