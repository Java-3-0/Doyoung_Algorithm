// 26292KB, 256ms

package baek11866;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		sc.close();

		// 조세푸스 순열 계산
		List<Integer> josephus = getJosephus(N, K);

		// 출력
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int i = 0; i < N - 1; i++) {
			sb.append(josephus.get(i)).append(", ");
		}
		sb.append(josephus.get(N - 1)).append(">\n");

		System.out.print(sb.toString());
	}

	/** (N, K)-조세푸스 순열을 리턴 */
	public static List<Integer> getJosephus(int N, int K) {
		List<Integer> josephus = new ArrayList<Integer>(N);

		// 1 ~ n번까지 순서대로 앉아 있는 큐
		Queue<Integer> queue = new LinkedList<Integer>();
		for (int i = 1; i <= N; i++) {
			queue.add(i);
		}

		// 모든 사람을 제거할 때까지 반복
		while (!queue.isEmpty()) {
			// (K - 1)명을 맨 앞에서 맨 뒤로 옮김
			for (int i = 0; i < K - 1; i++) {
				queue.add(queue.poll());
			}
			// 1명을 제거
			josephus.add(queue.poll());
		}

		return josephus;
	}
}
