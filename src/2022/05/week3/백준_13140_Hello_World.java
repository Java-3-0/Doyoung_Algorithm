// 12308KB, 124ms

package bj13140;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int NO_ANSWER = -1;
	static int N;
	static int[] permu = new int[7];
	static boolean[] isUsed = new boolean[10];
	static Answer answer = new Answer(NO_ANSWER, NO_ANSWER, NO_ANSWER);

	static class Answer {
		int num1;
		int num2;
		int sum;

		public Answer(int num1, int num2, int sum) {
			super();
			this.num1 = num1;
			this.num2 = num2;
			this.sum = sum;
		}

		@Override
		public String toString() {
			return "Answer [num1=" + num1 + ", num2=" + num2 + ", sum=" + sum + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		permutation(0);

		if (answer.num1 != NO_ANSWER) {
			sb.append(" ").append(String.format("%6d", answer.num1)).append("\n");
			sb.append("+").append(String.format("%6d", answer.num2)).append("\n");
			sb.append("-------").append("\n");
			
			sb.append(" ").append(String.format("%6d", answer.sum)).append("\n");
		} else {
			sb.append("No Answer").append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static void permutation(int startIdx) {
		if (startIdx == 7) {
			int h = permu[0];
			int w = permu[1];
			int e = permu[2];
			int o = permu[3];
			int l = permu[4];
			int r = permu[5];
			int d = permu[6];

			int num1 = 10000 * h + 1000 * e + 100 * l + 10 * l + 1 * o;
			int num2 = 10000 * w + 1000 * o + 100 * r + 10 * l + 1 * d;

			int sum = num1 + num2;

			if (sum == N) {
				answer.num1 = num1;
				answer.num2 = num2;
				answer.sum = sum;
			}

			return;
		}

		int beginNum = 0;
		if (startIdx <= 1) {
			beginNum = 1;
		}

		for (int num = beginNum; num <= 9; num++) {
			if (isUsed[num]) {
				continue;
			}

			isUsed[num] = true;
			permu[startIdx] = num;
			permutation(startIdx + 1);
			isUsed[num] = false;
		}
	}

}