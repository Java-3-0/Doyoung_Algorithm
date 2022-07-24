// 26812KB, 216ms

package boj25381;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		char[] text = br.readLine().toCharArray();
		int len = text.length;

		Queue<Integer> positionsA = new ArrayDeque<>();
		Queue<Integer> positionsB = new ArrayDeque<>();
		Queue<Integer> positionsC = new ArrayDeque<>();

		for (int i = 0; i < len; i++) {
			char c = text[i];
			switch (c) {
			case 'A':
				positionsA.add(i);
				break;
			case 'B':
				positionsB.add(i);
				break;
			case 'C':
				positionsC.add(i);
				break;
			default:
				break;
			}
		}

		int answer = 0;
		while (!positionsB.isEmpty()) {
			int here = positionsB.poll();

			// 필요없는 C 제거
			while (!positionsC.isEmpty() && positionsC.peek() <= here) {
				positionsC.poll();
			}

			// BC 먼저 시도
			if (!positionsC.isEmpty() && positionsC.peek() > here) {
				positionsC.poll();
				answer++;
			}

			// AB 시도
			else if (!positionsA.isEmpty() && positionsA.peek() < here) {
				positionsA.poll();
				answer++;
			}
		}

		System.out.println(answer);

	} // end main

}