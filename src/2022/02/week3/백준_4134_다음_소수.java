// 11944KB, 340ms

package bj4134;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			long N = Long.parseLong(br.readLine());
			sb.append(nextPrime(N)).append("\n");
		}

		System.out.print(sb.toString());
	}

	public static long nextPrime(long n) {
		if (n <= 2L) return 2L;
		
		// 홀수인 것만 탐색한다.
		if (n % 2L == 0L) {
			n += 1L;
		}
		
		for (long i = n; i <= Long.MAX_VALUE; i += 2) {
			if (isPrime(i)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static boolean isPrime(long n) {
		long sqrt = (long) Math.sqrt(n);
		for (long i = 2; i <= sqrt; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		
		return true;
	}
}