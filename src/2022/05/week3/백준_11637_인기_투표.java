// 11912KB, 80ms

package bj11637;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			int N = Integer.parseInt(br.readLine());

			int maxVote = 0;
			int winner = 0;
			int sum = 0;
			boolean existMultipleWinners = false;
			for (int candidate = 1; candidate <= N; candidate++) {
				int vote = Integer.parseInt(br.readLine());

				sum += vote;

				if (maxVote < vote) {
					maxVote = vote;
					winner = candidate;
					existMultipleWinners = false;
				}
				else if (maxVote == vote) {
					existMultipleWinners = true;
				}
			} // end for candidate

			int half = sum / 2;

			if (existMultipleWinners) {
				sb.append("no winner");
			} else {
				if (half < maxVote) {
					sb.append("majority winner ").append(winner);
				} else {
					sb.append("minority winner ").append(winner);
				}
			}

			sb.append("\n");

		} // end for tc

		System.out.print(sb.toString());

	} // end main
}