// 293276KB, 2096ms

package bj1990;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int INVALID = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();

		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());

		if (A == 2) {
			sb.append(A).append("\n");
		}
		if (A % 2 == 0) {
			A++;
		}
		
		for (int num = A; num <= B; num += 2) {
			if (isPalindrome(num) && isPrime(num)) {
				sb.append(num).append("\n");
			}
		}
		
		System.out.print(sb.toString());
		System.out.println("-1");
	}

	public static boolean isPalindrome(int n) {
		String s = String.valueOf(n);

		int lastIdx = s.length() - 1;
		int halfIdx = lastIdx / 2;
		for (int i = 0; i <= halfIdx; i++) {
			if (s.charAt(i) != s.charAt(lastIdx - i)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isPrime(int n) {
		long sqrt = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt; i++) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}
