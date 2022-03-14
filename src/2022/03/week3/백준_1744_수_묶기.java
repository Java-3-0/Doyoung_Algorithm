// 11628KB, 76ms

package bj1744;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 수열 크기 입력
		int N = Integer.parseInt(br.readLine());

		PriorityQueue<Long> positives = new PriorityQueue<>(Collections.reverseOrder());
		PriorityQueue<Long> negatives = new PriorityQueue<>();

		// 수열 입력
		for (int i = 0; i < N; i++) {
			long input = Long.parseLong(br.readLine());
			if (input > 0) {
				positives.add(input);
			} else {
				negatives.add(input);
			}
		}

		// 절대값이 큰 양수부터 처리하면서 곱한 것과 더한 것중 높은 쪽을 선택한다.
		long answer = 0L;
		while (!positives.isEmpty()) {
			long num1 = positives.poll();
			if (!positives.isEmpty()) {
				long num2 = positives.poll();
				long added = num1 + num2;
				long multiplied = num1 * num2;
				answer += Math.max(added, multiplied);
			} else {
				answer += num1;
			}
		}

		// 절대값이 큰 음수부터 처리하면서 곱한다.
		while (!negatives.isEmpty()) {
			long num1 = negatives.poll();
			if (!negatives.isEmpty()) {
				long num2 = negatives.poll();
				long multiplied = num1 * num2;
				answer += multiplied;
			} else {
				answer += num1;
			}
		}

		System.out.println(answer);
	}

}
