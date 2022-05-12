// 25892KB, 196ms

package bj13251;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int M = Integer.parseInt(br.readLine());
		
		int[] seq = new int[M];
		st = new StringTokenizer(br.readLine(), " ");
		int S = 0;
		for (int i = 0; i < M; i++) {
			int input = Integer.parseInt(st.nextToken());
			seq[i] = input;
			S += input;
		}

		int K = Integer.parseInt(br.readLine());
		
		BigInteger total = combination(S, K);
		
		BigInteger success = new BigInteger("0");
		for (int i = 0; i < M; i++) {
			success = success.add(combination(seq[i], K));
		}
		
		
		BigDecimal numer = new BigDecimal(success);
		BigDecimal denom = new BigDecimal(total);
		
		BigDecimal answer = numer.divide(denom, 20, BigDecimal.ROUND_HALF_EVEN);
		
		System.out.println(answer.toString());

	} // end main

	private static BigInteger combination(int n, int k) {
		BigInteger ret = new BigInteger("1");
		
		if (n < k) {
			return BigInteger.ZERO;
		}
		
		for (int i = n; i > n - k; i--) {
			ret = ret.multiply(new BigInteger(String.valueOf(i)));
		}
		
		for (int i = k; i >= 1; i--) {
			ret = ret.divide(new BigInteger(String.valueOf(i)));
		}
		
		return ret;
	}
}