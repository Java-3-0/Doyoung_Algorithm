// 12108KB, 92ms

package bj4903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while (true) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			if (A == -1 && B == -1) {
				break;
			}
			
			int sum = A + B;
			int comb = combination(sum, A);
			
			sb.append(A).append("+").append(B);
			if (comb == sum) {
				sb.append("=");
			}
			else {
				sb.append("!=");
			}
			sb.append(sum).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static int combination(int n, int k) {
		if (n < k) {
			return 0;
		}
		
		long ret = 1L;
		for (long i = (long) (n); i > (long) (n - k); i--) {
			ret *= i;
		}
		for (long i = (long) k; i >= 1L; i--) {
			ret /= i;
		}
		
		return (int) ret;
	}
}