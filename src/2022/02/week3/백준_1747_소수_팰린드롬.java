// 33916KB, 168ms

package bj1747;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int INVALID = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int answer = getNextPrimePalindrome(N);
		System.out.println(answer);

	}
	
	public static int getNextPrimePalindrome (int N) {
		if (N <= 2) {
			return 2;
		}
		
		if (N % 2 == 0) {
			N += 1;
		}
		for (int i = N; i <= Integer.MAX_VALUE; i += 2) {
			if (isPalindrome(i) && isPrime(i)) {
				return i;
			}
		}
		
		return INVALID;
	}
	
	public static boolean isPalindrome (int n) {
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
