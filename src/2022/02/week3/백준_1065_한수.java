// 12932KB, 112ms

package bj1065;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		int count = 0;
		for (int num = 1; num <= N; num++) {
			if (isHansu(num)) {
				count++;
			}
		}
		System.out.println(count);
	}
	
	/** 수가 한수이면 true, 아니면 false를 리턴 */
	public static boolean isHansu (int x) {
		int[] digits = getDigits(x);
		
		if (digits.length <= 1) return true;
		
		int diff = digits[1] - digits[0];
		for (int i = 2; i < digits.length; i++) {
			if (digits[i] - digits[i - 1] != diff) {
				return false;
			}
		}
		
		return true;
	}

	/** 수의 각 자리를 배열로 만들어서 리턴 */
	public static int[] getDigits (int x) {
		String s = String.valueOf(x);
	
		int[] ret = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			ret[i] = c - '0';
		}
		
		return ret;
	}
}
