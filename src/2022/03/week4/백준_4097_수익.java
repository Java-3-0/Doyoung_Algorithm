// 76152KB, 420ms

package bj4097;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_N = 250000;
	static final long INF = 987654321987654321L;

	static int N;
	static long[] psums = new long[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while ((N = Integer.parseInt(br.readLine())) != 0) {
			for (int i = 1, sum = 0; i <= N; i++) {
				long input = Long.parseLong(br.readLine());
				sum += input;
				psums[i] = sum;
			}

			long answer = getMaxProfit();
			
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	public static long getMaxProfit() {
		long ret = -INF;
		long prevMin = psums[0];
		
		for (int i = 1; i <= N; i++) {
			long profit = psums[i] - prevMin;
			ret = Math.max(ret, profit);
			if (psums[i] < prevMin) {
				prevMin = psums[i];
			}
		}
		
		return ret;
	}
	
	public static void printPsums() {
		for (int i = 0; i <= N; i++) {
			System.out.print(psums[i] + " ");
		}
		System.out.println();
	}

}