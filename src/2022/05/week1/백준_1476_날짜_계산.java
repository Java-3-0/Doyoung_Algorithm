// 11500KB, 76ms

package bj1476;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MOD_E = 15, MOD_S = 28, MOD_M = 19;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		int E = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		if (E == MOD_E) {
			E = 0;
		}
		if (S == MOD_S) {
			S = 0;
		}
		if (M == MOD_M) {
			M = 0;
		}

		for (int year = 1; year <= MOD_E * MOD_S * MOD_M; year++) {
			if (year % MOD_E == E && year % MOD_S == S && year % MOD_M == M) {
				System.out.println(year);
				break;
			}
		}
	} // end main

}