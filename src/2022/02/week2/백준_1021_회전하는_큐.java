// 17776KB, 216ms

package baek1021;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	public static int dequeSize;
	public static int targetSize;
	public static int[] targets;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 크기 입력
		dequeSize = sc.nextInt();
		targetSize = sc.nextInt();

		// 꺼내올 수들을 배열에 입력 받기
		targets = new int[targetSize];
		for (int i = 0; i < targetSize; i++) {
			targets[i] = sc.nextInt();
		}
		sc.close();

		// deque 생성 및 초기화
		Deque<Integer> deque = new LinkedList<>();
		for (int num = 1; num <= dequeSize; num++) {
			deque.add(num);
		}

		// 최소 이동 횟수 계산
		int answer = minShift(deque, 0);

		// 출력
		System.out.println(answer);
	}

	/** deque에서 target을 모두 꺼내오기까지의 shift 작업의 최소 횟수를 리턴 */
	public static int minShift(Deque<Integer> deque, int targetIdx) {
		// base case : 다 꺼냈다면 0
		if (targetIdx == targetSize) {
			return 0;
		}

		// 찾을 때까지 왼쪽으로 shift
		int count = 0;
		while (deque.peekFirst() != targets[targetIdx]) {
			deque.addLast(deque.pollFirst());
			count++;
		}

		// 이동 방향을 반대로 하는 것과 비교해서 적은 쪽 선택
		int minCount = Math.min(count, deque.size() - count);

		// 첫 원소 제거 후 재귀 호출
		deque.removeFirst();
		return minCount + minShift(deque, targetIdx + 1);
	}
}
