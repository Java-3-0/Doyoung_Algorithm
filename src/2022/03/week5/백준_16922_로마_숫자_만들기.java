// 11564KB, 80ms

package bj16922;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	static final int[] coins = { 1, 5, 10, 50 };
	static int[] permu = new int[4];
	static Set<Integer> answers = new HashSet<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		permutation(0, N);

		int answer = answers.size();

		System.out.println(answer);

	} // end main

	public static void permutation(int idx, int n) {
		// 순열이 완성된 경우
		if (idx == 3) {
			permu[idx] = n;

			int sum = 0;
			for (int i = 0; i < 4; i++) {
				sum += permu[i] * coins[i];
			}
			answers.add(sum);

			return;
		}

		// 순열이 완성되지 않은 경우
		for (int pick = 0; pick <= n; pick++) {
			permu[idx] = pick;
			permutation(idx + 1, n - pick);
		}

	}
}